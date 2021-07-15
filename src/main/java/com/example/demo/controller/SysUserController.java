package com.example.demo.controller;


import com.example.demo.bo.ChangePasswordBo;
import com.example.demo.bo.EditSysUserInfoBo;
import com.example.demo.bo.LoginBo;
import com.example.demo.bo.RegisterBo;
import com.example.demo.service.SysUserService;
import com.example.demo.util.ResultReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
//@CrossOrigin(allowCredentials="true",maxAge = 3600, origins = "*")
@RequestMapping("/sysUser")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login")
    public ResultReturn login(@RequestBody LoginBo loginBo){
        logger.info("loginBo=" + loginBo);
        return sysUserService.login(loginBo);
    }

    @RequestMapping("/changePassword")
    public ResultReturn test(@RequestBody ChangePasswordBo changePasswordBo, @RequestHeader("token") String token){
        changePasswordBo.setToken(token);
        logger.info("修改密码" + changePasswordBo.toString());
        return sysUserService.changePassword(changePasswordBo);
    }

    @RequestMapping("/register")
    public ResultReturn register(@RequestBody RegisterBo registerBo, @RequestHeader("token") String token){
        logger.info("注册" + registerBo.toString());
        return sysUserService.register(registerBo);
    }

    @RequestMapping("/editSysUserInfo")
    public ResultReturn edit(@RequestBody EditSysUserInfoBo sysUserInfoBo, @RequestHeader("token") String token){
        sysUserInfoBo.setToken(token);
        logger.info("编辑系统用户信息：" + sysUserInfoBo.toString());
        return sysUserService.editInfo(sysUserInfoBo);
    }


}
