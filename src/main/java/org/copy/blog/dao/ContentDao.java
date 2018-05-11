package org.copy.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.blog.domain.ContentDO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentDao {

    ContentDO get(Long cid);

    List<ContentDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(ContentDO content);

    int update(ContentDO content);

    int remove(Long cid);

    int batchRemove(Long[] cids);
}
