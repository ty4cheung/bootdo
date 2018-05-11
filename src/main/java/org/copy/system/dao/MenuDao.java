package org.copy.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.copy.system.domain.MenuDO;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {
    MenuDO get(Long menuId);

    List<MenuDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(MenuDO menu);

    int update(MenuDO menu);

    int remove(Long menuId);

    int batchRemove(Long[] menuIds);

    List<MenuDO> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);
}
