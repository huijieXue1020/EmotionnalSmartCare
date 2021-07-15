package com.example.demo.controller;

import com.example.demo.entry.Event;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.EventService;
import com.example.demo.socket.WebSocket;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/event")
public class EventController {

    private Logger logger = LoggerFactory.getLogger(EventController.class);


    @Resource
    private WebSocket webSocket;

    @Resource
    private EventService eventService;

    @RequestMapping("add")
    public ResultReturn sendMessage(@RequestBody Event event){

        logger.info("发生事件：" + event);

        List<Event> list = new ArrayList<>();
        String id = eventService.addEvent(event);
        event.setId(Integer.parseInt(id));
        list.add(event);
        webSocket.sendMessage(ResultReturnUtil.success("success", list));
        return ResultReturnUtil.success("添加成功", id);
    }

    @RequestMapping("picture")
    public ResultReturn addPicture(@RequestBody MultipartFile picture, @RequestHeader("id") String id){

        logger.info("添加图片:" + picture + "==>" + id);
        return eventService.addPicture(picture, id);
    }

    @RequestMapping(value = "getPicture", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getPicture(@RequestHeader("id") String id){

        logger.info("查看图片，事件id：" + id);
        return eventService.getPicture(id);
    }
}
