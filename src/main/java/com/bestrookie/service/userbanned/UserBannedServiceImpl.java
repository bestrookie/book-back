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
     * 分页查询用户封禁信息
     * @param param
     * @return
     */
    @Override
    public PageResult queryUserBannedInfo(PageRequestParam param) {
        return PageUtils.getPageResult(param,getUserBannedInfo(param));
    }

    /**
     * 根据用户id禁言
     * @param bannedPojo
     * @return
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
     * @param bannedId
     * @param rDate
     * @return
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
     * @param userId
     * @return
     */
    @Override
    public boolean isUserBanned(int userId) {
        UserBannedPojo userBannedPojo = userBannedMapper.queryBannedInfoById(userId);
        if (System.currentTimeMillis() < userBannedPojo.getRemoveDate()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 调用分页插件完成分页
     * @param param
     * @return
     */
    private PageInfo<UserBannedPojo> getUserBannedInfo(PageRequestParam param){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<UserBannedPojo> userBanns = userBannedMapper.queryBannedInfos();
        return new PageInfo<>(userBanns);
    }
}
