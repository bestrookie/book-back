package com.bestrookie.service.message;

import com.bestrookie.mapper.MessageMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.MessagePojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 14:45 2020/10/26
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    /**
     * 分页查询所有消息
     * @param param 分页信息
     * @param userId 用户id
     * @return 分页完成的消息列表
     */
    @Override
    public PageResult queryMsgByUserId(PageRequestParam param, int userId) {
        return PageUtils.getPageResult(param,getMessageInfo(param,userId));
    }
    /**
     * 保存即时消息
     * @param messagePojo 消息对象
     * @return 是否添加成功
     */
    @Override
    public boolean saveMessage(MessagePojo messagePojo) {
        return messageMapper.saveMessage(messagePojo);
    }
    /**
     * 已读
     * @param msgId 消息id
     * @return 已读结果
     */
    @Override
    public MyResult hasReadMsg(int msgId) {
        if (messageMapper.hasRead(msgId)){
            return MyResult.success(true,"已读");
        }else {
            return MyResult.failed("已读失败",false,514);
        }
    }
    /**
     * 已读所有消息
     * @param userId 用户id
     * @return 自定义结果集
     */
    @Override
    public MyResult hasReadMsgAll(int userId) {
        if (messageMapper.hasReadAll(userId)){
            return MyResult.success(true,"一键已读");
        }else {
            return MyResult.failed("已读失败",false,515);
        }
    }
    /**
     * 根据id查询消息
     * @param msgId 消息id
     * @return 消息实体
     */
    @Override
    public MessagePojo queryMessageById(int msgId) {
        return messageMapper.queryMessageById(msgId);
    }

    @Override
    public int queryMsg(int userId, int dynamicId) {
        return messageMapper.queryMsg(userId, dynamicId);
    }

    @Override
    public boolean updateFlg(int userId, int dynamicId) {
        return messageMapper.updateFlg(userId, dynamicId);
    }

    /**
     * 调用分页插件
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    private PageInfo<MessagePojo> getMessageInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<MessagePojo> messages = messageMapper.queryAllMessages(userId);
        return new PageInfo<>(messages);
    }
}
