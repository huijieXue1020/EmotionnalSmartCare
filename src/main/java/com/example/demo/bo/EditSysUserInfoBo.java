package com.example.demo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditSysUserInfoBo {

    private String userName;
    private String realName;
    private String email;
    private String token;
    private String createTime;
}
