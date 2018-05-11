package org.copy.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeptDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long deptId;
    //上级部门ID，一级部门为0
    private Long parentId;
    //部门名称
    private String name;
    //排序
    private Integer orderNum;
    //是否删除  -1：已删除  0：正常
    private Integer delFlag;
}
