package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carolin_violet.travel_system.bean.vo.FeedbackVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
public interface FeedbackService extends IService<Feedback> {

    void addFeedback(FeedbackVo feedbackVo);
}
