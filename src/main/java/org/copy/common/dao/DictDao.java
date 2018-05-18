package org.copy.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.common.domain.DictDO;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 * @author wykj-4
 */
@Mapper
public interface DictDao {

    DictDO get(Long id);

    List<DictDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DictDO dict);

    int update(DictDO dict);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<DictDO> listType();
}
