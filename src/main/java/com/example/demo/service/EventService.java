package com.example.demo.service;

import com.example.demo.entry.Event;
import com.example.demo.mapper.EventMapper;
import com.example.demo.mapper.PicturesMapper;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class EventService {

    @Resource
    private EventMapper eventMapper;

    @Resource
    private PicturesMapper picturesMapper;

    public String addEvent(Event event){
        eventMapper.addEvent(event);

        return eventMapper.getIdByDate(event.getEventDate(), event.getOldPerson());
    }

    public ResultReturn addPicture(MultipartFile picture, String id){

        if (picture == null)
            return ResultReturnUtil.fail("图片为空");

        String contentType = picture.getContentType();
//        if (contentType != null && !contentType.split("/")[0].equals("image"))
//            return ResultReturnUtil.fail("文件类型错误");

        return save(picture, id);
    }

    private ResultReturn save(MultipartFile picture, String id){
        String basePath = "/root/java/eventPics";

//        String basePath = "E:\\java\\pictures";
        File file = new File(basePath);
        if (!file.exists())
            file.mkdirs();

        try {

            String filename = picture.getOriginalFilename();
            assert filename != null;
            if (picturesMapper.getPictureByDir(basePath + File.separator  + filename)!=null)
                return ResultReturnUtil.success("图片已存在");
            picture.transferTo(new File(basePath + File.separator  + filename));   //保存在磁盘上
            picturesMapper.savePictures(id, basePath + File.separator  + filename);
        }catch (IOException e){
            e.printStackTrace();
        }

        return ResultReturnUtil.success("图片添加成功");
    }

    public byte[] getPicture(String id){
        String path = eventMapper.getPathById(id);
        File file = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



}
