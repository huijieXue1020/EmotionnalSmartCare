package com.example.demo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordBo {

    private String token;
    private String oldPassword;
    private String newPassword;
}
