package org.copy.system.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.copy.common.config.BootdoConfig;
import org.copy.common.domain.FileDO;
import org.copy.common.domain.Tree;
import org.copy.common.service.FileService;
import org.copy.common.utils.*;
import org.copy.system.dao.DeptDao;
import org.copy.system.dao.UserDao;
import org.copy.system.dao.UserRoleDao;
import org.copy.system.domain.DeptDO;
import org.copy.system.domain.UserDO;
import org.copy.system.domain.UserRoleDO;
import org.copy.system.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.expression.Lists;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public int updatePersonal(UserDO userDO){
        return userDao.update(userDO);
    }

    @Transactional
    public int batchRemove(Long[] userIds) {
        int count = userDao.batchRemove(userIds);
        userRoleDao.batchRemoveByUserId(userIds);
        return count;
    }

    public int adminResetPwd(UserVO userVO) throws Exception{
        UserDO userDO =get(userVO.getUserDO().getUserId());
        if("admin".equals(userDO.getUsername())){
            throw new Exception("超级管理员的账号不允许直接重置！");
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return userDao.update(userDO);
    }

    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptDao.list(new HashMap<>(16));
        Long[] pDepts = deptDao.listParentDept();
        Long[] uDepts = userDao.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts,dept.getDeptId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<>();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String,Object> state = new HashMap<>(16);
            state.put("opened",true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> userDOS = userDao.list(new HashMap<>(16));
        for (UserDO userDO : userDOS){
            Tree<DeptDO> tree = new Tree<>();
            tree.setId(userDO.getUserId().toString());
            tree.setParentId(userDO.getDeptId().toString());
            tree.setText(userDO.getDeptName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    public Map<String,Object> updatePersonalImg(MultipartFile file,String avatar_data,Long userId) throws Exception{
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        FileDO sysFile = new FileDO(FileType.fileType(fileName),"/files/"+ fileName,new Date());
        // 获取图片后缀
        String prefix = fileName.substring(fileName.lastIndexOf('.')+1);
        String[] str = avatar_data.split(",");
        //获取截取的x坐标
        int x = (int)Math.floor(Double.parseDouble(str[0].split(":")[1]));
        //获取截取的y坐标
        int y = (int)Math.floor(Double.parseDouble(str[1].split(":")[1]));
        //获取截取的高度
        int h = (int)Math.floor(Double.parseDouble(str[2].split(":")[1]));
        //获取截取的宽度
        int w = (int)Math.floor(Double.parseDouble(str[3].split(":")[1]));
        //获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file,x,y,w,h,prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(rotateImage, prefix, out);
            //转换后存入数据库
            byte[] b = out.toByteArray();
            FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
        } catch (Exception e) {
            throw  new Exception("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        if(sysFileService.save(sysFile)>0){
            UserDO userDO = new UserDO();
            userDO.setUserId(userId);
            userDO.setPicId(sysFile.getId());
            if(userDao.update(userDO)>0){
                result.put("url",sysFile.getUrl());
            }
        }
        return result;
    }
}
