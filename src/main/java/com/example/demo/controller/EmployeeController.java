package com.example.demo.controller;

import com.example.demo.bo.DeleteByIdBo;
import com.example.demo.bo.LoginBo;
import com.example.demo.bo.PicturesBo;
import com.example.demo.bo.QueryUserBo;
import com.example.demo.entry.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.PicturesService;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private EmployeeService employeeService;

    @Resource
    private PicturesService picturesService;

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("login")
    public ResultReturn login(@RequestBody LoginBo loginBo){

        logger.info("员工登陆：" + loginBo);
        return employeeService.login(loginBo);
    }

    @RequestMapping("/add")
    public ResultReturn add(@RequestBody Employee employee){
        logger.info("添加员工：" + employee);
        return employeeService.add(employee);
    }

    @RequestMapping("/update")
    public ResultReturn update(@RequestBody Employee employee){
        logger.info("更新员工信息：" + employee);
        return employeeService.update(employee);
    }

    @RequestMapping("/delete")
    public ResultReturn delete(@RequestBody DeleteByIdBo id){
        logger.info("删除员工：" + id);
        return employeeService.delete(id.getId());
    }

    @RequestMapping("/getAll")
    public ResultReturn getAll(){
        logger.info("查询员工信息");
        return employeeService.getAll();
    }

    @RequestMapping("/pictures")
    public ResultReturn submitPictures(@RequestBody MultipartFile[] pictures, @RequestHeader("token") String token){
        PicturesBo picturesBo = new PicturesBo();
        picturesBo.setPictures(pictures);
        picturesBo.setToken(token);
        picturesBo.setType("employee");

        logger.info("接受员工图片：" + picturesBo.toString());

        return picturesService.savePictures(picturesBo);
    }

    @RequestMapping("getByUserName")
    public ResultReturn getByUserName(@RequestBody QueryUserBo queryUserBo){
        logger.info("查询员工：" + queryUserBo.getUsername());
        return employeeService.queryByUsername(queryUserBo.getUsername());
    }

}
