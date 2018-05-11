package org.copy.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.system.domain.UserDO;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    UserDO get(Long userId);

    List<UserDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(UserDO user);

    int update(UserDO user);

    int remove(Long userId);

    int batchRemove(Long[] userIds);

    Long[] listAllDept();

}