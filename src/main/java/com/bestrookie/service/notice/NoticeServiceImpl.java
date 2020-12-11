package com.bestrookie.service.notice;

import com.bestrookie.mapper.NoticeMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.NoticePojo;
import com.bestrookie.service.noticesread.NoticesReadService;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:06 2020/10/27
 */
@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticesReadService readService;
    /**
     * 查询未读公告
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    @Override
    public PageResult queryNotice(PageRequestParam param,int userId) {
        return PageUtils.getPageResult(param,getPageInfo(param,userId));
    }

    /**
     * 查询所有公告
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryAllNotice(PageRequestParam param) {
        return PageUtils.getPageResult(param,getPageInfo(param,0));
    }
    /**
     * 管理员删除公告
     * @param noticeId 公告id
     * @return 自定义返回类型
     */
    @Override
    public MyResult deleteNotice(int noticeId) {
        if (noticeMapper.deleteNotice(noticeId)){
            return MyResult.success(true,"删除成功");
        }else {
            return MyResult.failed("删除失败",false,515);
        }
    }

    /**
     * 发布公告
     * @param noticePojo 公告信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult releaseNotice(NoticePojo noticePojo) {
        if (noticeMapper.addNotice(noticePojo)){
            return MyResult.success(noticePojo.getNoticeId(),"发布成功");
        }else {
            return  MyResult.failed("发布失败",515);
        }
    }

    /**
     * 根据id查询公告
     * @param noticeId 公告id
     * @return 公告信息
     */
    @Override
    public NoticePojo queryNoticeById(int noticeId) {
        return noticeMapper.queryNoticeById(noticeId);
    }

    /**
     * 查询调用分页插件
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    private PageInfo<NoticePojo> getPageInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<NoticePojo> notices = noticeMapper.queryNotice();
        if (userId != 0){
            for (NoticePojo notice : notices){
                if (readService.isReadNotice(userId,notice.getNoticeId())){
                    notice.setRead(true);
                }
            }
        }
        return new PageInfo<>(notices);
    }
}
