package org.copy.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.copy.common.config.Constant;
import org.copy.common.utils.HttpServletUtils;
import org.copy.common.utils.R;
import org.copy.common.utils.ShiroUtils;
import org.copy.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class BDExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return R.error(403, "未授权");
        }
        return new ModelAndView("error/403");
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e, HttpServletRequest request) {
//        LogDO logDO = new LogDO();
//        logDO.setGmtCreate(new Date());
//        logDO.setOperation(Constant.LOG_ERROR);
//        logDO.setMethod(request.getRequestURL().toString());
//        logDO.setParams(e.toString());
        UserDO current = ShiroUtils.getUser();
        if(null!=current){
//            logDO.setUserId(current.getUserId());
//            logDO.setUsername(current.getUsername());
        }
//        logService.save(logDO);
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return R.error(500, "服务器错误，请联系管理员");
        }
        return new ModelAndView("error/500");
    }
}
