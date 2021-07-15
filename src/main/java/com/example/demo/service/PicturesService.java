package com.example.demo.service;

import com.example.demo.bo.PicturesBo;
import com.example.demo.controller.OldPersonController;
import com.example.demo.mapper.PicturesMapper;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;

@Service
public class PicturesService {

    private Logger logger = LoggerFactory.getLogger(PicturesService.class);

    @Resource
    private RedisService redisService;

    @Resource
    private RestTemplate restTemplate;

//    @Resource
//    private PicturesMapper picturesMapper;

    public ResultReturn savePictures(PicturesBo picturesBo){
        if (picturesBo.getPictures().length == 0)
            return ResultReturnUtil.fail("图片为空");

        for (int i = 0; i < picturesBo.getPictures().length; i++) {
            String contentType = picturesBo.getPictures()[i].getContentType();
            if (contentType != null && !contentType.split("/")[0].equals("image"))
                return ResultReturnUtil.fail("文件类型错误");
        }

        return save(picturesBo);
    }

    private ResultReturn save(PicturesBo picturesBo){
        MultipartFile[] pictures = picturesBo.getPictures();
        String type = picturesBo.getType();
        String token = picturesBo.getToken();

        String username = (String)redisService.get(token);
//        String basePath = "/Users/dengzhiwen/pictures/" + type + "/" + username;
        String basePath = "/root/java/pictures/" + type + "/" + username;
        File file = new File(basePath);
        if (!file.exists())
            file.mkdirs();

        for (int i = 0; i < pictures.length; i++) {
            try {
                String filename = pictures[i].getOriginalFilename();
                assert filename != null;
//                if (picturesMapper.getPictureByDir(basePath + File.separator  + filename)!=null)
//                    return ResultReturnUtil.success("图片已存在");
                pictures[i].transferTo(new File(basePath + File.separator  + filename));   //保存在磁盘上
//                picturesMapper.savePictures(type, username, basePath + File.separator  + filename);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        String url = "http://localhost:5000/train_face";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String string = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        logger.info(string);

        return ResultReturnUtil.success("训练完成");
    }
}
