package com.services;

import org.springframework.stereotype.Service;

@Service
public interface BaseService<T> {

    T saveOrUpdate(T t);
    void delete(T t);

}
