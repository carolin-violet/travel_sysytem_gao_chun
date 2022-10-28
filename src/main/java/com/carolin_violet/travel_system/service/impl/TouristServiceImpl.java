package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Comment;
import com.carolin_violet.travel_system.bean.Feedback;
import com.carolin_violet.travel_system.bean.Tourist;
import com.carolin_violet.travel_system.bean.TravelNote;
import com.carolin_violet.travel_system.mapper.TouristMapper;
import com.carolin_violet.travel_system.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-30
 */
@Service
public class TouristServiceImpl extends ServiceImpl<TouristMapper, Tourist> implements TouristService {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private CommentService commentService;

    @Override
    public void removeAll(String id) {


        // 删除评论
        QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("tourist_id", id);
        commentService.remove(wrapper2);


        try {
            System.out.println("88888888888888888888888888888");
            // 删除反馈
            QueryWrapper<Feedback> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("tourist_id", id);
            List<Feedback> feedbackList = feedbackService.list(wrapper1);

            // 删除游记
            QueryWrapper<TravelNote> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("tourist_id", id);
            List<TravelNote> travelNoteList = travelNoteService.list(wrapper3);
            System.out.println("9999999999999999999999999999999");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
