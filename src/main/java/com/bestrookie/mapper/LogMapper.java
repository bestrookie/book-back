package com.bestrookie.mapper;

import com.bestrookie.pojo.LogPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:55 2020/10/29
 */
@Repository
@Mapper
public interface LogMapper {
    /**
     * 添加日志信息
     * @param logPojo
     * @return
     */
    boolean addLogInfo(LogPojo logPojo);

    /**
     * 查看日志信息
     * @return
     */
    List<LogPojo> queryLogInfo();
}
