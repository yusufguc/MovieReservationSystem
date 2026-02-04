package com.yusufguc.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<E> {

    private  Integer status;

    private ExceptionDetail<E> exception;
}
