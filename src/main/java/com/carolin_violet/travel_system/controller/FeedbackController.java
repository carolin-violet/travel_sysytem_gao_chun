package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Feedback;
import com.carolin_violet.travel_system.bean.TouristPhotos;
import com.carolin_violet.travel_system.bean.vo.FeedbackVo;
import com.carolin_violet.travel_system.bean.vo.ReplayVo;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.FeedbackService;
import com.carolin_violet.travel_system.service.TouristPhotosService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@RestController
@RequestMapping("/travel_system/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TouristPhotosService touristPhotosService;

    // 分页查询反馈信息
    @SystemControllerLog(operate = "分页查询反馈信息",module = "反馈管理")
    @PreAuthorize("hasAnyAuthority('ROLE_FEEDBACK')")
    @RequestMapping("/pageFeedback/{current}/{limit}")
    public R getPageFeedback(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType) {
        Page<Feedback> feedbackPage = new Page<>(current, limit);
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        feedbackService.page(feedbackPage, wrapper);

        long total = feedbackPage.getTotal();
        List<Feedback> records = feedbackPage.getRecords();

        List<FeedbackVo> feedbackVos = new ArrayList<>();

        for (Feedback feedback: records) {
            FeedbackVo feedbackVo = new FeedbackVo();
            BeanUtils.copyProperties(feedback, feedbackVo);
            List<String> photoList = new ArrayList<>();
            List<TouristPhotos> touristPhotos = touristPhotosService.list(new QueryWrapper<TouristPhotos>().eq("mark_id", feedbackVo.getId()));
            for (TouristPhotos touristPhoto: touristPhotos) {
                photoList.add(touristPhoto.getUrl());
            }
            feedbackVo.setPhotoUrlList(photoList);

            feedbackVos.add(feedbackVo);
        }

        return R.ok().data("rows", feedbackVos).data("total", total);
    }

    // 删除反馈
    @SystemControllerLog(operate = "删除反馈",module = "反馈管理")
    @PreAuthorize("hasAnyAuthority('ROLE_FEEDBACK')")
    @DeleteMapping("{id}")
    public R removeFeedback(@PathVariable String id) {
        touristPhotosService.removePhotos(id);
        feedbackService.removeById(id);
        return R.ok();
    }

    // 官方回复反馈
    @SystemControllerLog(operate = "回复反馈",module = "反馈管理")
    @PreAuthorize("hasAnyAuthority('ROLE_FEEDBACK')")
    @PatchMapping("{id}/replay")
    public R removeFeedback(@PathVariable String id, @RequestBody ReplayVo replayVo) {

        Feedback feedback = feedbackService.getById(id);
        feedback.setReplay(replayVo.getReplay());
        feedback.setReplayTime(new Date());
        feedbackService.updateById(feedback);
        return R.ok();
    }
}

