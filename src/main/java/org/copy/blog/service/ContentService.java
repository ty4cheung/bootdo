package org.copy.blog.service;

import org.copy.blog.dao.ContentDao;
import org.copy.blog.domain.ContentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentService {
    @Autowired
    private ContentDao bContentMapper;

    public ContentDO get(Long cid){
        return bContentMapper.get(cid);
    }

    public List<ContentDO> list(Map<String, Object> map){
        return bContentMapper.list(map);
    }

    public int count(Map<String, Object> map){
        return bContentMapper.count(map);
    }

    
    public int save(ContentDO bContent){
        return bContentMapper.save(bContent);
    }

    public int update(ContentDO bContent){
        return bContentMapper.update(bContent);
    }

    public int remove(Long cid){
        return bContentMapper.remove(cid);
    }

    public int batchRemove(Long[] cids){
        return bContentMapper.batchRemove(cids);
    }
}
