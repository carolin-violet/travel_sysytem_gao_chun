package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-21
 */
public interface ArticleService extends IService<Article> {

    void addArticle(Article article);

}
