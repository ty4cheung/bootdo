package org.copy.oa.domain;

import lombok.Data;

@Data
public class NotifyDTO extends NotifyDO {
    private static final long serialVersionUID = 1L;

    private String isRead;

    private String before;

    private String sender;
}
