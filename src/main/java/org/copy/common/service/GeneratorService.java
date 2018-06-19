package org.copy.common.service;

import org.apache.commons.io.IOUtils;
import org.copy.common.dao.GeneratorMapper;
import org.copy.common.utils.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorService {
    @Autowired
    GeneratorMapper generatorMapper;

   public List<Map<String,Object>> list(){
        List<Map<String, Object>> list = generatorMapper.list();
        return list;
    }

    public byte[] generatorCode(String tableNames[]) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName :tableNames) {
            Map<String, String> table = generatorMapper.get(tableName);
            // 查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            // 生成代码
            GenUtils.generatorCode(table,columns,zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
