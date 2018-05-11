package org.copy.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.system.domain.RoleDO;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao {
    RoleDO get(Long roleId);

    List<RoleDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(RoleDO role);

    int update(RoleDO role);

    int remove(Long roleId);

    int batchRemove(Long[] roleIds);
}
