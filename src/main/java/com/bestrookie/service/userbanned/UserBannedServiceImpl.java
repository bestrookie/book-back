package com.bestrookie.service.userbanned;

import com.bestrookie.mapper.UserBannedMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.UserBannedPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:31 2020/10/22
 */
@Service
public class UserBannedServiceImpl implements UserBannedService{
    @Autowired
    private UserBannedMapper userBannedMapper;

    /**
     * 分页查询禁言信息
     * @param param 分页信息
     * @param type 查询类型
     * @return 自定义返回类型
     */
    @Override
    public PageResult queryUserBannedInfo(PageRequestParam param,int type) {
        return PageUtils.getPageResult(param,getUserBannedInfo(param,type));
    }

    /**
     * 根据用户id禁言
     * @param bannedPojo 禁言实体信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult bannedUserById(UserBannedPojo bannedPojo) {
        boolean flg = userBannedMapper.addBannedInfo(bannedPojo);
        if (flg){
            return MyResult.success(true);
        }else {
            return MyResult.failed("禁言失败",false,514);
        }
    }

    /**
     * 解除禁言
     * @param bannedId 禁言信息id
     * @param rDate 解禁时间
     * @return 自定义返回类型
     */
    @Override
    public MyResult removeBannedById(int bannedId, long rDate) {
        boolean flg = userBannedMapper.removeBannedInfoById(bannedId, rDate);
        if (flg){
            return MyResult.success(true);
        }else {
            return  MyResult.failed("解封失败",false,514);
        }
    }

    /**
     * 查询用户是否被禁言
     * @param userId 用户id
     * @return 是否被禁言
     */
    @Override
    public boolean isUserBanned(int userId) {
        UserBannedPojo userBannedPojo = userBannedMapper.queryBannedInfoById(userId);
        return System.currentTimeMillis() < userBannedPojo.getRemoveDate();
    }

    /**
     * 调用分页插件分页
     * @param param 分页信息
     * @param type 查询类型
     * @return 分页查询结果
     */
    private PageInfo<UserBannedPojo> getUserBannedInfo(PageRequestParam param,int type){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<UserBannedPojo> userBanns;
        switch (type){
            case 0:
                userBanns = userBannedMapper.queryBanned(System.currentTimeMillis());
                break;
            case 1:
                userBanns = userBannedMapper.queryUnBanned(System.currentTimeMillis());
                break;
            default:
                userBanns = userBannedMapper.queryBannedInfos();
        }

        return new PageInfo<>(userBanns);
    }
}
