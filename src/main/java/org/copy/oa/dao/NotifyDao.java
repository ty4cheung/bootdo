package org.copy.oa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.oa.domain.NotifyDO;
import org.copy.oa.domain.NotifyDTO;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 * @author wykj-4
 */
@Mapper
public interface NotifyDao {
    NotifyDO get(Long id);

    List<NotifyDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(NotifyDO notify);

    int update(NotifyDO notify);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<NotifyDO> listByIds(Long[] ids);

    int countDTO(Map<String, Object> map);

    List<NotifyDTO> listDTO(Map<String, Object> map);
}
