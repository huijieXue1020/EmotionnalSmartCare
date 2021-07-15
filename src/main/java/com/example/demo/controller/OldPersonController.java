package com.example.demo.controller;

import com.example.demo.bo.DeleteByIdBo;
import com.example.demo.bo.LoginBo;
import com.example.demo.bo.PicturesBo;
import com.example.demo.bo.QueryUserBo;
import com.example.demo.entry.OldPerson;
import com.example.demo.service.OldPersonService;
import com.example.demo.service.PicturesService;
import com.example.demo.util.ResultReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
//@CrossOrigin(allowCredentials="true",maxAge = 3600, origins = "*")
@RequestMapping("/oldPerson")
public class OldPersonController {

    private Logger logger = LoggerFactory.getLogger(OldPersonController.class);

    @Resource
    private OldPersonService oldPersonService;

    @Resource
    private PicturesService picturesService;


    @RequestMapping("/login")
    public ResultReturn login(@RequestBody LoginBo loginBo){

        logger.info("老人登陆：" + loginBo);
        return oldPersonService.login(loginBo);
    }

    @RequestMapping("/add")
    public ResultReturn add(@RequestBody OldPerson oldPerson){

        logger.info("添加老人：" + oldPerson);
        return oldPersonService.add(oldPerson);
    }

    @RequestMapping("/update")
    public ResultReturn update(@RequestBody OldPerson oldPerson){

        logger.info("更新老人信息：" + oldPerson);
        return oldPersonService.update(oldPerson);
    }

    @RequestMapping("/delete")
    public ResultReturn delete(@RequestBody DeleteByIdBo id){

        logger.info("删除老人信息：" + id);
        return oldPersonService.delete(id.getId());
    }

    @RequestMapping("/getAll")
    public ResultReturn getAll(){

        logger.info("查询老人信息");
        return oldPersonService.getAll();
    }

    @RequestMapping("/pictures")
    public ResultReturn submitPictures(@RequestBody MultipartFile[] pictures, @RequestHeader("token") String token){
        PicturesBo picturesBo = new PicturesBo();
        picturesBo.setPictures(pictures);
        picturesBo.setToken(token);
        picturesBo.setType("oldPerson");

        logger.info("接受图片：" + picturesBo.toString());

        return picturesService.savePictures(picturesBo);
    }

    @RequestMapping("getByUserName")
    public ResultReturn getByUserName(@RequestBody QueryUserBo queryUserBo){
        logger.info("查询老人：" + queryUserBo.getUsername());
        return oldPersonService.queryByUsername(queryUserBo.getUsername());
    }
}
