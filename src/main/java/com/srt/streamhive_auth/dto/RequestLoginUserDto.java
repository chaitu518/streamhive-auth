package com.srt.streamhive_auth.dto;

import lombok.Data;

@Data
public class RequestLoginUserDto {
    private String email;
    private String password;
}