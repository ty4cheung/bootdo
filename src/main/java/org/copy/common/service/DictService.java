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

    public DictDO get(Long id){
        return dictDao.get(id);
    }

    public List<DictDO> list(Map<String, Object> map){
        return dictDao.list(map);
    }

    public int count(Map<String, Object> map){
        return dictDao.count(map);
    }

    public int save(DictDO dict){
        return dictDao.save(dict);
    }

    public int update(DictDO dict){
        return dictDao.update(dict);
    }

    public int remove(Long id){
        return dictDao.remove(id);
    }

    public int batchRemove(Long[] ids){
        return dictDao.batchRemove(ids);
    }

    public List<DictDO> listType(){
        return dictDao.listType();
    }

    public String getName(String type,String value){
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
    public List<DictDO> getHobbyList(UserDO userDO){
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
    public List<DictDO> getSexList(){
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return dictDao.list(param);
    }

    /**
     * 根据type获取数据
     * @return
     */
    public List<DictDO> listByType(String type){
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return dictDao.list(param);
    }
}
