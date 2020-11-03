package com.bestrookie.handler;

import com.bestrookie.pojo.MessagePojo;
import com.bestrookie.pojo.NoticePojo;
import com.bestrookie.service.message.MessageService;
import com.bestrookie.service.notice.NoticeService;
import com.bestrookie.utils.TokenUtils;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
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
    private MessageService messageService;
    @Autowired
    private NoticeService noticeService;
    public static ConcurrentHashMap<String, SocketIOClient> clientHashMap = new ConcurrentHashMap<>();

    /**
     * 客户端连接
     * @param client 登录用户
     */
    @OnConnect
    public void onConnect(SocketIOClient client){
        client.sendEvent("MESSAGE","onConnect back");
    }


    /**
     * 设置登录信息
     * @param client 登录客户
     * @param data 消息实体
     */
    @OnEvent(value = "set_info")
    public void receiveMsg(SocketIOClient client, MessagePojo data){
        int userId = TokenUtils.getId(data.getMsg());
        clientHashMap.put(String.valueOf(userId),client);
        log.info("客户端:" + client.getSessionId() + "uid=" + userId);
    }

    /**
     * 发送评论消息
     * @param data 消息实体
     */
    @OnEvent(value = "send_review")
    public void sendReview( MessagePojo data){
        int targetId = data.getTargetId();
        data.setType(1);
        data.setMsgDate(System.currentTimeMillis());
        data.setUserId(TokenUtils.getId(data.getToken()));
        messageService.saveMessage(data);
        SocketIOClient targetClient = clientHashMap.get(String.valueOf(targetId));
        data = messageService.queryMessageById(data.getMsgId());
        if (targetClient != null){
            targetClient.sendEvent("REVIEW",data);
        }
    }
    /**
     * 发布点赞消息
     * @param data 消息实体
     */
    @OnEvent(value = "send_like")
    public void sendLike( MessagePojo data){
        int targetId = data.getTargetId();
        data.setType(0);
        data.setMsgDate(System.currentTimeMillis());
        data.setUserId(TokenUtils.getId(data.getToken()));
        messageService.saveMessage(data);
        SocketIOClient targetClient = clientHashMap.get(String.valueOf(targetId));
        data = messageService.queryMessageById(data.getMsgId());
        if(messageService.queryMsg(data.getUserId(),data.getDynamicId()) == 1){
            if (targetClient != null){
                targetClient.sendEvent("LIKE",data);
            }
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

    /**
     * 向用户发送举报已经处理
     * @param userId 用户id
     */
    @OnEvent(value = "solve_report")
    public void solveReport(int userId){
        MessagePojo msg = new MessagePojo();
        msg.setUserId(1);
        msg.setAvatarUrl("image/01.png");
        msg.setTargetId(userId);
        msg.setMsgDate(System.currentTimeMillis());
        msg.setType(2);
        msg.setUserName("admin");
        msg.setMsg("你的举报已经受理，感谢为网络环境做出的贡献");
        messageService.saveMessage(msg);
        SocketIOClient client = clientHashMap.get(String.valueOf(userId));
        if (client != null){
            client.sendEvent("SYSTEM",msg);
        }
    }

    /**
     * 向用户发送禁言通知
     * @param msg 信息实体
     */
    @OnEvent(value = "banned")
    public void banned(MessagePojo msg){
        msg.setType(2);
        msg.setAvatarUrl("image/01.png");
        msg.setUserName("admin");
        msg.setMsgDate(System.currentTimeMillis());
        msg.setUserId(1);
        messageService.saveMessage(msg);
        SocketIOClient client = clientHashMap.get(String.valueOf(msg.getTargetId()));
        if (client != null){
            client.sendEvent("SYSTEM",msg);
        }
    }

    /**
     * 注销是移除client
     * @param msg 消息实体
     */
    @OnEvent(value = "logout")
    public void logout(MessagePojo msg){
        int userId  = TokenUtils.getId(msg.getToken());
        clientHashMap.remove(String.valueOf(userId));
    }

    /**
     * 收到举报信息
     */
    @OnEvent(value = "receive_report")
    public void receiveReport(){
        SocketIOClient client = clientHashMap.get("1");
        client.sendEvent("REPORT","收到一条举报信息");
    }
}
