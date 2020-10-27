package com.bestrookie.mapper;

import com.bestrookie.pojo.MessagePojo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:28 2020/10/26
 */
@Mapper
@Repository
public interface MessageMapper {
    /**
     * 查询某个人的所有消息
     * @param userId : 用户id
     * @return 消息列表
     */
    List<MessagePojo>queryAllMessages(int userId);
    /**
     * 保存消息
     * @param messagePojo : 消息实体
     * @return 是否成功
     */
    boolean saveMessage(MessagePojo messagePojo);
    /**
     * 已读一条评论
     * @param msgId : 消息id
     * @return 是否修改成功
     */
    boolean hasRead(int msgId);
    /**
     * 已读全部消息
     * @param userId : 用户id
     * @return 是否成功
     */
    boolean hasReadAll(int userId);

    /**
     * 根据id查询msg信息
     * @param msgId : 消息id
     * @return 消息实体
     */
    MessagePojo queryMessageById(int msgId);
    /**
     * 查询是否存在此消息
     * @param userId
     * @param dynamicId
     * @return
     */
    int queryMsg(@Param(value = "userId") int userId, @Param(value = "dynamicId") int dynamicId);

    /**
     * 给多余消息打标记
     * @param userId
     * @param dynamicId
     * @return
     */
    boolean updateFlg(@Param(value = "userId") int userId,@Param(value = "dynamicId") int dynamicId);
}
