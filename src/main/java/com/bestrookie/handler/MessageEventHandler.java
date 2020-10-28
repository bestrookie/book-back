package com.bestrookie.handler;

import com.bestrookie.pojo.MessagePojo;
import com.bestrookie.pojo.NoticePojo;
import com.bestrookie.service.message.MessageService;
import com.bestrookie.service.notice.NoticeService;
import com.bestrookie.utils.TokenUtils;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : bestrookie
 * @date : 15:13 2020/10/25
 */
@Component
@Slf4j
public class MessageEventHandler {
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private MessageService messageService;
    @Autowired
    private NoticeService noticeService;
    public static ConcurrentHashMap<String, SocketIOClient> clientHashMap = new ConcurrentHashMap<>();

    /**
     * 客户端连接
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client){
        client.sendEvent("MESSAGE","onConnect back");
    }


    /**
     * 设置登录信息
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(value = "set_info")
    public void receiveMsg(SocketIOClient client, AckRequest request, MessagePojo data){
        int userId = TokenUtils.getId(data.getMsg());
        clientHashMap.put(String.valueOf(userId),client);
        log.info("客户端:" + client.getSessionId() + "uid=" + userId);
    }

    /**
     * 发送评论消息
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(value = "send_review")
    public void sendReview(SocketIOClient client, AckRequest request, MessagePojo data){
        int targetId = data.getTargetId();
        data.setType(1);
        data.setMsgDate(System.currentTimeMillis());
        data.setUserId(TokenUtils.getId(data.getToken()));
        messageService.saveMessage(data);
        SocketIOClient targetClient = clientHashMap.get(String.valueOf(targetId));
        data = messageService.queryMessageById(data.getMsgId());
        targetClient.sendEvent("REVIEW",data);
    }
    /**
     * 发布点赞消息
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(value = "send_like")
    public void sendLike(SocketIOClient client, AckRequest request, MessagePojo data){
        int targetId = data.getTargetId();
        data.setType(0);
        data.setMsgDate(System.currentTimeMillis());
        data.setUserId(TokenUtils.getId(data.getToken()));
        messageService.saveMessage(data);
        SocketIOClient targetClient = clientHashMap.get(String.valueOf(targetId));
        data = messageService.queryMessageById(data.getMsgId());
        if(messageService.queryMsg(data.getUserId(),data.getDynamicId()) == 1){
            targetClient.sendEvent("LIKE",data);
        }
    }

    /**
     * 公告广播
     */
    @OnEvent(value = "send_notice")
    public void sendNotice(int noticeId){
        NoticePojo noticePojo = noticeService.queryNoticeById(noticeId);
        for (SocketIOClient client : clientHashMap.values()){
            if (client.isChannelOpen()){
                client.sendEvent("NOTICE",noticePojo);
            }
        }
    }
}
