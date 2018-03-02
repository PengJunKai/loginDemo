package com.peng.utils.exception;

import com.peng.utils.tips.ErrorTip;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip runtimeError(RuntimeException e) {
        ErrorTip tip;
        if(e instanceof AppException){
            AppException appException = (AppException) e;
            tip = new ErrorTip(appException.getErrCode(),appException.getMessage(),e);
        }else {
            tip = new ErrorTip(ExceptionType.RUNTIME_ERROR.getCode(), ExceptionType.RUNTIME_ERROR.getDefaultMsg(),e);
        }
        return tip;
    }

}
