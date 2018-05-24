package org.copy.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.common.domain.LogDO;

import java.util.List;
import java.util.Map;
@Mapper
public interface LogDao {

    LogDO get(Long id);

    List<LogDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(LogDO log);

    int update(LogDO log);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
