package com.uchain.meetingapp.exception;

import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        //处理自定义的业务异常
        if (e instanceof GlobalException){
            GlobalException globalException = (GlobalException) e;
            log.error(globalException.getMessage());
            return Result.error(globalException.getCodeMsg());
        }

        if (e instanceof BadCredentialsException){
            log.error("密码错误..");
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        if (e instanceof BindException){
            BindException exception = (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            String message = null;
            for (ObjectError error : allErrors) {
                if (message!=null){
                    message = message+","+error.getDefaultMessage();
                }else {
                    message = error.getDefaultMessage();
                }
            }
            log.error(message);
            Result<String> enumResult = Result.error(CodeMsg.BIND_ERROR);
            enumResult.setMsg(String.format(enumResult.getMsg(),message));
            return enumResult;
        }else {
            log.error("系统错误..");
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
