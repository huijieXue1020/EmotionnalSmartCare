package com.example.demo.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Volunteer {
    private int id;
    private int orgId;
    private int clientId;
    private String name;
    private String password;
    private String gender;  //性别
    private String phone;
    private String id_card;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String birthday;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String checkin_date;  //入职

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String checkout_date;  //离职

    private String imgSetDir;
    private String profilePhotoDir;

    private String description;
    private String isActive;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int createBy;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private int updateBy;
    private String remove; //删除标志
}
