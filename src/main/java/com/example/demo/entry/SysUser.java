package com.example.demo.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class SysUser{

    private int id;
    private int orgId;
    private int clientId;
    private String username;
    private String password;
    //用户真实名字
    private String realName;
    private String sex;
    private String email;
    private String phone;
    private String mobile;
    private String description;
    private String isActive;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    private int createBy;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
    private int updateBy;
    private char remove;
    private String dataFilter;
    private String theme;
    private String defaultPage;
    private String loginImage;
    private String qqOpenId;
    private String appVersion;
    private String jsonAuth;

}
