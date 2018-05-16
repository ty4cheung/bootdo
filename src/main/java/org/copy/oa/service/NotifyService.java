package org.copy.oa.service;

import org.copy.common.utils.DateUtils;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.copy.oa.dao.NotifyDao;
import org.copy.oa.domain.NotifyDTO;
import org.copy.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotifyService {

    @Autowired
    private NotifyDao notifyDao;

    @Autowired
    private UserDao userDao;

    public PageUtils selfList(Map<String, Object> map) {
        List<NotifyDTO> rows = notifyDao.listDTO(map);
        for (NotifyDTO notifyDTO : rows) {
            notifyDTO.setBefore(DateUtils.getTimeBefore(notifyDTO.getUpdateDate()));
            notifyDTO.setSender(userDao.get(notifyDTO.getCreateBy()).getName());
        }
        PageUtils page = new PageUtils(rows, notifyDao.countDTO(map));
        return page;
    }
}
