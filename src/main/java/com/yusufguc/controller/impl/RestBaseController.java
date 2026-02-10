package com.yusufguc.controller.impl;

import com.yusufguc.model.RootEntity;
import com.yusufguc.utils.PagerUtil;
import com.yusufguc.utils.RestPageableEntity;
import com.yusufguc.utils.RestPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RestBaseController {

    public <T> RootEntity<T> ok(T data){
        return RootEntity.ok(data);
    }

    public <T> RootEntity<T> error(String errorMessage){
        return RootEntity.error(errorMessage);
    }

    public Pageable toPageable(RestPageableRequest request){
        return PagerUtil.toPageable(request);
    }

    public <T>RestPageableEntity<T> toPageableResponse(Page<T> page, List<T> content){
        return PagerUtil.toPageableResponse(page,content);
    }

}
