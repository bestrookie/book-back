package com.bestrookie.mapper;
import com.bestrookie.pojo.UserBannedPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:56 2020/10/21
 */
@Mapper
@Repository
public interface UserBannedMapper {
    /**
     * 添加封禁信息
     * @param userBannedPojo
     * @return
     */
    boolean addBannedInfo(UserBannedPojo userBannedPojo);
    /**
     * 查询所有的封禁信息
     * @return
     */
    List<UserBannedPojo> queryBannedInfos();
    /**
     * 根据用户id查询封禁信息
     * @param userId
     * @return
     */
    UserBannedPojo queryBannedInfoById(int userId);
    /**
     * 解除禁言
     * @param bannedId
     * @param rDate
     * @return
     */
    boolean removeBannedInfoById(@Param(value = "bannedId") int bannedId,@Param(value = "rDate") long rDate);
}
