package com.carolin_violet.travel_system.service.impl;

import com.carolin_violet.travel_system.bean.Feedback;
import com.carolin_violet.travel_system.bean.vo.FeedbackVo;
import com.carolin_violet.travel_system.mapper.FeedbackMapper;
import com.carolin_violet.travel_system.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {


    // 添加反馈
    @Override
    public void addFeedback(FeedbackVo feedbackVo) {
        // 1. 添加反馈获取id
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackVo, feedback);
        baseMapper.insert(feedback);
    }
}
