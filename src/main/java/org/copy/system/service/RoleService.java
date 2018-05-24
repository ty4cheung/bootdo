package org.copy.system.service;

import org.copy.system.dao.RoleDao;
import org.copy.system.dao.RoleMenuDao;
import org.copy.system.dao.UserDao;
import org.copy.system.dao.UserRoleDao;
import org.copy.system.domain.RoleDO;
import org.copy.system.domain.RoleMenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleDao roleDao;

    @Autowired
    RoleMenuDao roleMenuMapper;

    @Autowired
    UserDao userMapper;

    @Autowired
    UserRoleDao userRoleMapper;

    public RoleDO get(Long id){
        RoleDO roleDO = roleDao.get(id);
        return roleDO;
    }

    public List<RoleDO> list(){
        List<RoleDO> roleDOS = roleDao.list(new HashMap<>(16));
        return roleDOS;
    }

    @Transactional
    public int save(RoleDO role){
        int count = roleDao.save(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId:menuIds){
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setMenuId(menuId);
            rmDo.setRoleId(roleId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0){
            roleMenuMapper.batchSave(rms);
        }
        return count;
    }

    public int update(RoleDO role){
        int r = roleDao.update(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return r;
    }

    public int remove(Long id){
        int count = roleDao.remove(id);
        userRoleMapper.removeByRoleId(id);
        roleMenuMapper.removeByRoleId(id);
        return count;
    }

    public List<RoleDO> list(Long userId){
        List<Long> rolesIds = userRoleMapper.listRoleId(userId);
        List<RoleDO> roles = roleDao.list(new HashMap<>(16));
        for (RoleDO roleDO :roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(),roleId)){
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    public int batchremove(Long[] ids){
        int r = roleDao.batchRemove(ids);
        return r;
    }
}
