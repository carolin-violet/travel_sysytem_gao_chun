package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Article;
import com.carolin_violet.travel_system.bean.Hotel;
import com.carolin_violet.travel_system.mapper.ArticleMapper;
import com.carolin_violet.travel_system.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-21
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

//    @Autowired
//    private HotelService hotelService;
//
//    @Autowired
//    private DelicacyService delicacyService;
//
//    @Autowired
//    private ScenicSpotService scenicSpotService;
//
//    @Autowired
//    private NoticeService noticeService;

    @Override
    public void addArticle(Article article) {
        baseMapper.insert(article);
    }
}
