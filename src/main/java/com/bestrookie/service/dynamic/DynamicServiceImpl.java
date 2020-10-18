package com.bestrookie.service.dynamic;

import com.bestrookie.mapper.DynamicMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.DynamicPojo;
import com.bestrookie.service.givelike.GiveLikeService;
import com.bestrookie.utils.PageUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:16 2020/10/15
 */
@Service
public class DynamicServiceImpl implements DynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private GiveLikeService giveLikeService;

    /**
     * 查询动态信息
     * @param param
     * @param discussionId
     * @return
     */
    @Override
    public PageResult queryDynamic(PageRequestParam param,int discussionId,int userId) {
        return PageUtils.getPageResult(param,getDynamicInfo(param,discussionId,userId));
    }
    /**
     * 发布动态
     * @param dynamicPojo
     * @return
     */
    @SneakyThrows
    @Override
    public MyResult releaseDynamic(DynamicPojo dynamicPojo) {
        if (dynamicPojo == null){
            return MyResult.failed("动态信息不能为空",null,409);
        }else {
            SensitiveWordUtils.init();
            if (SensitiveWordUtils.contains(dynamicPojo.getDAbstract())){
                return MyResult.failed("内容违规",null,409);
            }else {
                if (dynamicMapper.addDynamic(dynamicPojo)){
                    return MyResult.success(true,"发布成功");
                }else {
                    return MyResult.failed("发布失败",false,509);
                }
            }
        }
    }

    /**
     * 查看动态详情
     * @param dynamicId
     * @param userId
     * @return
     */
    @Override
    public MyResult queryDynamicById(int dynamicId,int userId) {
        if(dynamicId > 0 ){
            DynamicPojo dynamicPojo = dynamicMapper.queryDynamicById(dynamicId);
            if (dynamicPojo == null){
                return MyResult.failed("查询失败",null,509);
            }else {
                HashMap<String, Object> result = new HashMap<>();
                if (dynamicPojo.getUser().getUserId() == userId){
                    result.put("my",true);
                }else {
                    result.put("my",false);
                }
                dynamicPojo.setLike(giveLikeService.isLiked(dynamicId,userId));
                result.put("dynamic",dynamicPojo);
                return MyResult.success(result);
            }
        }else {
            return MyResult.failed("参数信息错误",null,409);
        }
    }

    /**
     * 删除动态
     * @param dynamicId
     * @param userId
     * @return
     */
    @Override
    public MyResult deleteDynamicById(int dynamicId, int userId) {
        if (dynamicId > 0 || userId > 0){
            if (dynamicMapper.deleteDynamicById(dynamicId,userId)){
                return MyResult.success(true);
            }else {
                return MyResult.failed("删除失败",null,509);
            }
        }else {
            return MyResult.failed("参数错误",null,409);
        }
    }

    /**
     * 调用分页插件完成分页
     * @param param
     * @return
     */
    private PageInfo<DynamicPojo> getDynamicInfo(PageRequestParam param,int discussionId,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<DynamicPojo> dynamics = dynamicMapper.queryDynamic(discussionId);
        for (DynamicPojo dynamic : dynamics) {
            dynamic.setLike(giveLikeService.isLiked(dynamic.getDId(),userId));
        }
        return new PageInfo<DynamicPojo>(dynamics);
    }

}
