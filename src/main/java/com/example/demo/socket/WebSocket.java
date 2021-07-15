package com.example.demo.socket;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.mapper.EventMapper;
import com.example.demo.util.EncoderClassVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/ws/webSocket", encoders = {EncoderClassVo.class})
public class WebSocket {

    private Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        copyOnWriteArraySet.add(this);
        logger.info("websocket有新的连接, 总数:"+ copyOnWriteArraySet.size());
    }

    @OnClose
    public void onClose(){
        copyOnWriteArraySet.remove(this);
        logger.info("websocket连接断开, 总数:"+ copyOnWriteArraySet.size());
    }

    @OnMessage
    public void onMessage(String message){
        logger.info("websocket收到客户端发来的消息:"+message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("发生错误：" + error.getMessage() + "; sessionId:" + session.getId());
        error.printStackTrace();
    }
    
    public void sendMessage(Object object){
        for (WebSocket webSocket : copyOnWriteArraySet) {

            logger.info("websocket广播消息：" + object.toString());
            try {
                webSocket.session.getBasicRemote().sendObject(object);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message){
        for (WebSocket webSocket : copyOnWriteArraySet) {
            logger.info("websocket广播消息：" + message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
