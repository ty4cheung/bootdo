package org.copy.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptDao {

    DeptDO get(Long deptId);

    List<DeptDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(DeptDO dept);

    int update(DeptDO dept);

    int remove(Long deptId);

    int batchRemove(Long[] deptIds);

    Long[] listParentDept();

    int getDeptUserNumber(Long deptId);
}
