package com.yusufguc.exception.base;

import com.yusufguc.exception.message.ErrorMessage;

public class BaseException extends  RuntimeException{

    public  BaseException(ErrorMessage errorMessage){

        super(errorMessage.prepareErrorMessage());
    }
}
