package org.copy.common.service;

import org.copy.common.config.BootdoConfig;
import org.copy.common.dao.FileDao;
import org.copy.common.domain.FileDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
@Service
public class FileService {
    @Autowired
    private FileDao sysFileMapper;

    @Autowired
    private BootdoConfig bootdoConfig;
    
    public FileDO get(Long id){
        return sysFileMapper.get(id);
    }

    
    public List<FileDO> list(Map<String, Object> map){
        return sysFileMapper.list(map);
    }

    
    public int count(Map<String, Object> map){
        return sysFileMapper.count(map);
    }

    
    public int save(FileDO sysFile){
        return sysFileMapper.save(sysFile);
    }

    
    public int update(FileDO sysFile){
        return sysFileMapper.update(sysFile);
    }

    
    public int remove(Long id){
        return sysFileMapper.remove(id);
    }

    
    public int batchRemove(Long[] ids){
        return sysFileMapper.batchRemove(ids);
    }

    
    public Boolean isExist(String url) {
        Boolean isExist = false;
        if (!StringUtils.isEmpty(url)) {
            String filePath = url.replace("/files/", "");
            filePath = bootdoConfig.getUploadPath() + filePath;
            File file = new File(filePath);
            if (file.exists()) {
                isExist = true;
            }
        }
        return isExist;
    }
}
