package com.codeforcode.compile.dto;

import lombok.Data;

@Data
public class CompileRequest {
    private String language;
    private String code;
}