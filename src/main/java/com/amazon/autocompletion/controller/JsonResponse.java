package com.amazon.autocompletion.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JsonResponse {

    private String keyword;
    private Integer volume;

}
