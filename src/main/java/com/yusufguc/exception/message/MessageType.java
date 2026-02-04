package com.yusufguc.exception.message;


import lombok.Getter;

@Getter
public enum MessageType {

    GENERAL_EXCEPTION("9999","A general error occurred");

    private final String  code;
    private final String  message;


    MessageType(String code,String message){
        this.code=code;
        this.message=message;
    }
}
