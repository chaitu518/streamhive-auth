package com.srt.streamhive_auth.dto;

import lombok.Data;

@Data
public class RequestRegisterUserDto {
    private String email;
    private String password;
}
