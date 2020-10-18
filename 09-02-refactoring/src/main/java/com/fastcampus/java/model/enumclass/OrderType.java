package com.fastcampus.java.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderType {

    ALL(0,"묶음","모든 상품을 묶음 발송"),
    EACH(1,"개별","모든 상품을 준비되는대로 발송")
    ;

    private Integer id;
    private String title;
    private String description;
}
