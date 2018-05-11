package org.copy.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.common.domain.FileDO;

import java.util.List;
import java.util.Map;
@Mapper
public interface FileDao {
    FileDO get(Long id);

    List<FileDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(FileDO file);

    int update(FileDO file);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
