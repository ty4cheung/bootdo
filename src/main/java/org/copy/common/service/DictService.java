package org.copy.common.service;

import org.copy.common.dao.DictDao;
import org.copy.common.domain.DictDO;
import org.copy.common.utils.StringUtils;
import org.copy.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DictService {

    @Autowired
    DictDao dictDao;

    DictDO get(Long id){
        return dictDao.get(id);
    }

    List<DictDO> list(Map<String, Object> map){
        return dictDao.list(map);
    }

    int count(Map<String, Object> map){
        return dictDao.count(map);
    }

    int save(DictDO dict){
        return dictDao.save(dict);
    }

    int update(DictDO dict){
        return dictDao.update(dict);
    }

    int remove(Long id){
        return dictDao.remove(id);
    }

    int batchRemove(Long[] ids){
        return dictDao.batchRemove(ids);
    }

    List<DictDO> listType(){
        return dictDao.listType();
    }

    String getName(String type,String value){
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", type);
        param.put("value", value);
        return dictDao.list(param).get(0).getName();
    }

    /**
     * 获取爱好列表
     * @return
     * @param userDO
     */
    List<DictDO> getHobbyList(UserDO userDO){
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "hobby");
        List<DictDO> hobbyList = dictDao.list(param);

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }
        return hobbyList;
    }

    /**
     * 获取性别列表
     * @return
     */
    List<DictDO> getSexList(){
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return dictDao.list(param);
    }

    /**
     * 根据type获取数据
     * @return
     */
    List<DictDO> listByType(String type){
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return dictDao.list(param);
    }
}
