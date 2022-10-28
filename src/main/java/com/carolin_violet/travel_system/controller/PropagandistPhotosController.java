package com.carolin_violet.travel_system.controller;


import com.carolin_violet.travel_system.bean.PropagandistPhotos;
import com.carolin_violet.travel_system.bean.vo.PagePhotosVo;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.PropagandistPhotosService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  宣传图片的图库管理
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-18
 */
@RestController
@RequestMapping("/travel_system/propagandist-photos")
@CrossOrigin
public class PropagandistPhotosController {

    @Autowired
    private PropagandistPhotosService propagandistPhotosService;

    // 条件查询图片，如果是宣传图片就可以根据图片对应的详细名称查询相关图片，比如（高淳老街、水漫城），支持模糊查询
    @SystemControllerLog(operate = "条件查询图片",module = "宣传图片管理")
    @PreAuthorize("hasAnyAuthority('ROLE_PROPAGANDIST_PHOTOS')")
    @GetMapping
    public R getPhotos(@RequestParam String mark, @RequestParam String name) {
        // 根据物品name进行模糊查询，name为（例如景点的名称或者id）
        List<PropagandistPhotos> photos = propagandistPhotosService.getPhotos(mark, name);
        return R.ok().data("list", photos);
    }

    // 根据对应详细物品的id查询图片
    @SystemControllerLog(operate = "根据对应详细物品的id查询图片",module = "宣传图片管理")
    @PreAuthorize("hasAnyAuthority('ROLE_PROPAGANDIST_PHOTOS')")
    @GetMapping("{id}/{current}/{limit}")
    public R getPhotosByMarkId(@PathVariable String id, @PathVariable Long current, @PathVariable Long limit) {
        PagePhotosVo pagePhotosVo = propagandistPhotosService.getPhotosByMarkId(id, current, limit);
        return R.ok().data("list", pagePhotosVo.getPhotoList()).data("total", pagePhotosVo.getTotal());
    }

    // 根据图片类型查询对应类型的所有内容(在图库页面上传文件时选择图片类型时可以查询对应表中的元素)
    @SystemControllerLog(operate = "根据图片类型查询对应类型的所有内容",module = "宣传图片管理")
    @PreAuthorize("hasAnyAuthority('ROLE_PROPAGANDIST_PHOTOS')")
    @GetMapping("markNames")
    public R getMarkNames(@RequestParam String mark) {
        List nameList = propagandistPhotosService.getMarkNames(mark);
        return R.ok().data("list", nameList);
    }

    // 上传图片
//    @SystemControllerLog(operate = "上传图片",module = "宣传图片管理") // 日志记录导致图片连续上传3张相同的bug
    @PreAuthorize("hasAnyAuthority('ROLE_PROPAGANDIST_PHOTOS')")
    @PostMapping
    public R addPhoto(@RequestParam Map<String,String> params, MultipartFile file) {
        PropagandistPhotos propagandistPhoto = propagandistPhotosService.addPhoto(params.get("mark"), params.get("markId"), file);
        return R.ok().data("photoObj", propagandistPhoto);
    }

    // 根据id删除图片
    @SystemControllerLog(operate = "根据id删除图片",module = "宣传图片管理")
    @PreAuthorize("hasAnyAuthority('ROLE_PROPAGANDIST_PHOTOS')")
    @DeleteMapping("{id}")
    public R deletePhoto(@PathVariable String id) {
        propagandistPhotosService.deletePhotoById(id);
        return R.ok();
    }

}

