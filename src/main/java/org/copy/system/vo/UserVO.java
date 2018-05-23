package org.copy.system.vo;

import lombok.Data;
import org.copy.system.domain.UserDO;

@Data
public class UserVO {
    /**
            * 更新的用户对象
     */
    private UserDO userDO = new UserDO();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

}
