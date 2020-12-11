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
     * @param userBannedPojo 封禁信息
     * @return 是否添加成功
     */
    boolean addBannedInfo(UserBannedPojo userBannedPojo);
    /**
     * 查询所有的封禁信息
     * @return 封禁信息列表
     */
    List<UserBannedPojo> queryBannedInfos();
    /**
     * 根据用户id查询封禁信息
     * @param userId 用户id
     * @return 用户封禁信息
     */
    UserBannedPojo queryBannedInfoById(int userId);
    /**
     * 解除禁言
     * @param bannedId 封禁id
     * @param rDate 解除时间
     * @return 是否修改成功
     */
    boolean removeBannedInfoById(@Param(value = "bannedId") int bannedId,@Param(value = "rDate") long rDate);

    /**
     * 查询正在封禁
     * @param nowDate 查询正在封禁用户
     * @return 封禁列表
     */
    List<UserBannedPojo> queryBanned(long nowDate);
    /**
     * 查询已经解封
     * @param nowDate 查询已经解封
     * @return 封禁列表
     */
    List<UserBannedPojo> queryUnBanned(long nowDate);
}
