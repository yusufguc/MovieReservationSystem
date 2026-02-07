package com.yusufguc.exception.message;


import lombok.Getter;

@Getter
public enum MessageType {

    MOVIE_ALREADY_EXISTS("1001","Movie already exists"),
    THERE_IS_NO_MOVIE("1002","There is no movie of this ID"),
    NO_MOVIES_RECORDED("1003","No movies recorded"),
    THERE_IS_NO_HALL("1004","There is no hall of this ID"),
    SHOWTIME_CONFLICT("1005","The salon is already occupied at the specified time range"),
    THERE_IS_NO_SHOWTIME("1006","there is no Showtime of this ID"),
    GENERAL_EXCEPTION("9999","A general error occurred");

    private final String  code;
    private final String  message;


    MessageType(String code,String message){
        this.code=code;
        this.message=message;
    }
}
