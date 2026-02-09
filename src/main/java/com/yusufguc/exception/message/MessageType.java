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
    THERE_IS_NO_SEATS("1007","there is no seats"),
    SEAT_ALREADY_RESERVED("1008","Seat already reserved"),
    SHOWTIME_ALREADY_STARTED("1009","Showtime already started"),
    REFRESH_TOKEN_INVALID("1010","Refresh token invalid"),
    REFRESH_TOKEN_EXPIRED("1011","Refresh token expired"),
    USERNAME_ALREADY_EXISTS("1012","Username already exists"),
    GENERAL_EXCEPTION("9999","A general error occurred");

    private final String  code;
    private final String  message;


    MessageType(String code,String message){
        this.code=code;
        this.message=message;
    }
}
