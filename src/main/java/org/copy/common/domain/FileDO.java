package org.copy.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long id;
    // 文件类型
    private Integer type;
    // URL地址
    private String url;
    // 创建时间
    private Date createDate;

}
