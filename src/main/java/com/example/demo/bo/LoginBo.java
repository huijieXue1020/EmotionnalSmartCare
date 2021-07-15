package com.example.demo.bo;

import com.example.demo.entry.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginBo {

    private String username;
    private String password;

    public LoginBo(){

    }

    public LoginBo(SysUser sysUser){
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
    }

}
