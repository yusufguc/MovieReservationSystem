package com.yusufguc.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.model.ApiError;
import com.yusufguc.exception.model.ExceptionDetail;
import com.yusufguc.model.enums.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(value ={MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        Map<String, List<String>> map =new HashMap<>();
        for (ObjectError objectError:exception.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError) objectError).getField();

            if (map.containsKey(fieldName)) {
                map.put(fieldName,addValue(map.get(fieldName),objectError.getDefaultMessage()));
            }else {
                map.put(fieldName,addValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }
        return  ResponseEntity.badRequest().body(createApiError(map,request));
    }

    private List<String> addValue(List<String> list,String newValue){
        list.add(newValue);
        return  list;
    }



    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<String>> handleBaseException(BaseException exception, WebRequest request){
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }

    public <E> ApiError<E> createApiError(E message,WebRequest request){
        ApiError<E> apiError =new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        com.yusufguc.exception.model.ExceptionDetail<E> exception=new ExceptionDetail<>();
        exception.setCreateTime(new Date());
        exception.setHostName(getHostname());
        exception.setPath(request.getDescription(false).substring(4));
        exception.setMessage(message);

        apiError.setException(exception);
        return apiError;
    }

    private  String getHostname(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("an error has occurred " + e.getMessage());
        }
        return  null;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            WebRequest request){

        String message = "The JSON format is invalid.";

        Throwable cause = exception.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {

            Class<?> targetType = invalidFormatException.getTargetType();

            if (targetType.isEnum()) {

                Object[] enumConstants = targetType.getEnumConstants();

                StringBuilder acceptedValues = new StringBuilder("[");
                for (int i = 0; i < enumConstants.length; i++) {
                    acceptedValues.append(enumConstants[i]);
                    if (i < enumConstants.length - 1) {
                        acceptedValues.append(", ");
                    }
                }
                acceptedValues.append("]");

                message = "The enum value was sent incorrectly. Accepted values: " + acceptedValues;
            }
        }

        return ResponseEntity.badRequest().body(createApiError(message, request));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError<String>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        String message = "Invalid parameter value.";

        Class<?> requiredType = ex.getRequiredType();

        if (requiredType != null) {

            if (requiredType.isEnum()) {

                Object[] enumValues = requiredType.getEnumConstants();

                message = "Invalid enum value. Accepted values: " +
                        Arrays.toString(enumValues);
            }

            else if (requiredType.equals(Long.class)) {

                message = "Movie id must be a numeric value.";
            }
        }

        return ResponseEntity.badRequest().body(createApiError(message, request));
    }


}
