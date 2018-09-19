package com.dome.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {

    private Integer code;

    private String msg;
}
