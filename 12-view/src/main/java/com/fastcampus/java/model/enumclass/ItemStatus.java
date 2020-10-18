package com.fastcampus.java.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemStatus {

    REGISTERED(0,"등록","상품 등록 상태"),
    UNREGISTERED(1,"해지","상품 해지 상태"),
    WAITING(2,"검수(대기)","상품 검수 상태")
    ;

    private Integer id;
    private String title;
    private String description;
}
