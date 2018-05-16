package org.copy.oa.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class NotifyDO implements Serializable {
    private static final Long serialVersionUID = 1L;
    //编号
    private Long id;
    //类型
    private String type;
    //标题
    private String title;
    //内容
    private String content;
    //附件
    private String files;
    //状态
    private String status;
    //创建者
    private Long createBy;
    //创建时间
    private Date createDate;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateDate;
    //备注信息
    private String remarks;
    //删除标记
    private String delFlag;

    private Long[] userIds;
}
