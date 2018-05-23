package org.copy.system.service;

import lombok.extern.slf4j.Slf4j;
import org.copy.common.config.BootdoConfig;
import org.copy.common.service.FileService;
import org.copy.common.utils.MD5Utils;
import org.copy.system.dao.DeptDao;
import org.copy.system.dao.UserDao;
import org.copy.system.dao.UserRoleDao;
import org.copy.system.domain.UserDO;
import org.copy.system.domain.UserRoleDO;
import org.copy.system.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.expression.Lists;

import java.util.*;

@Transactional
@Service
@Slf4j
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    DeptDao deptDao;

    @Autowired
    private FileService sysFileService;

    @Autowired
    BootdoConfig bootdoConfig;

    public UserDO get(Long id){
        List<Long> roleIds = userRoleDao.listRoleId(id);
        UserDO userDO = userDao.get(id);
        userDO.setDeptName(deptDao.get(userDO.getDeptId()).getName());
        userDO.setRoleIds(roleIds);
        return userDO;
    }

    public List<UserDO> list(Map<String,Object> map) {
        List<UserDO> list = userDao.list(map);
        return list;
    }

    public int count(Map<String,Object> map){
        return userDao.count(map);
    }

    @Transactional
    public int save(UserDO userDO) {
        int count = userDao.save(userDO);
        Long userId = userDO.getUserId();
        List<Long> roles = userDO.getRoleIds();
        userRoleDao.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId :roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0){
            userRoleDao.batchSave(list);
        }
        return count;
    }

    public int update(UserDO user) {
        int r = userDao.update(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleDao.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleDao.batchSave(list);
        }
        return r;
    }

    public int remove(Long userId) {
        userRoleDao.removeByUserId(userId);
        return userDao.remove(userId);
    }

    public boolean exit(Map<String,Object> params) {
        boolean exit = false;
        List<UserDO> list = userDao.list(params);
        if (list != null) {
            exit = !list.isEmpty();
        }
        return exit;
    }

    public Set<String> listRoles(Long userId) {
        return null;
    }

    public int resetPwd(UserVO userVO, UserDO userDO) throws Exception {
        if(Objects.equals(userVO.getUserDO().getUserId(),userDO.getUserId())){
            if(Objects.equals(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdOld()),userDO.getPassword())){
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdNew()));
                return userDao.update(userDO);
            }else{
                throw new Exception("输入的旧密码有误！");
            }
        }else{
            throw new Exception("你修改的不是你登录的账号！");
        }
    }
}
