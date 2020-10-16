package com.bestrookie.service.dynamic;

import com.bestrookie.mapper.DynamicMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.DynamicPojo;
import com.bestrookie.utils.PageUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:16 2020/10/15
 */
@Service
public class DynamicServiceImpl implements DynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 查询动态信息
     * @param param
     * @param discussionId
     * @return
     */
    @Override
    public PageResult queryDynamic(PageRequestParam param,int discussionId) {
        return PageUtils.getPageResult(param,getDynamicInfo(param,discussionId));
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
            if (SensitiveWordUtils.contains(dynamicPojo.getDContent())){
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
     * 调用分页插件完成分页
     * @param param
     * @return
     */
    private PageInfo<DynamicPojo> getDynamicInfo(PageRequestParam param,int discussionId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<DynamicPojo> dynamics = dynamicMapper.queryDynamic(discussionId);
        return new PageInfo<DynamicPojo>(dynamics);
    }

}
