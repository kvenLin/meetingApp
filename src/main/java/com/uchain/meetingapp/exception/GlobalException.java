package com.uchain.meetingapp.exception;

import com.uchain.meetingapp.result.CodeMsg;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super();
        this.codeMsg = codeMsg;
    }
}
