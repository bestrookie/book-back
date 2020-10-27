package com.bestrookie.service.message;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.MessagePojo;
import org.apache.ibatis.annotations.Param;

/**
 * @author : bestrookie
 * @date : 14:43 2020/10/26
 */
public interface MessageService {
    /**
     * 查询某个用户的所有消息
     * @param param 分页参数
     * @param userId 用户id
     * @return : 分页结果
     */
    PageResult queryMsgByUserId(PageRequestParam param,int userId);
    /**
     * 保存即时消息
     * @param messagePojo 消息对象
     * @return 是否成功
     */
    boolean saveMessage(MessagePojo messagePojo);
    /**
     * 已读
     * @param msgId 消息id
     * @return 返回参数
     */
    MyResult hasReadMsg(int msgId);
    /**
     * 已读所有消息
     * @param userId 用户id
     * @return 自定义结果集
     */
    MyResult hasReadMsgAll(int userId);

    /**
     * 根据id查询消息
     * @param msgId 消息id
     * @return 消息实体
     */
    MessagePojo queryMessageById(int msgId);
    /**
     * 查询是否存在此消息
     * @param userId
     * @param dynamicId
     * @return
     */
    int queryMsg(int userId,int dynamicId);
    /**
     * 给多余消息打标记
     * @param userId
     * @param dynamicId
     * @return
     */
    boolean updateFlg(int userId,int dynamicId);
}
