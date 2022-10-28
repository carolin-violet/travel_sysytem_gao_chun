package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Article;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.ArticleService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-21
 */
@RestController
@RequestMapping("/travel_system/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 发布文章
//    @SystemControllerLog(operate = "发布文章",module = "文章管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTICLE')")
    @PostMapping
    public R addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
        return R.ok();
    }

    // 根据mark_id获取文章
    @SystemControllerLog(operate = "根据mark_id获取文章",module = "文章管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTICLE')")
    @GetMapping("{id}")
    public R getArticleById(@PathVariable String id) {
        Article article = articleService.getOne(new QueryWrapper<Article>().eq("mark_id", id));
        return R.ok().data("article", article);
    }

    // 更新文章
    @SystemControllerLog(operate = "更新文章",module = "文章管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTICLE')")
    @PutMapping
    public R updateArticle(@RequestBody Article article) {
        articleService.updateById(article);
        return R.ok();
    }

}

