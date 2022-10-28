package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.TouristPhotos;
import com.carolin_violet.travel_system.bean.TravelNote;
import com.carolin_violet.travel_system.bean.vo.TravelNoteVo;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.TouristPhotosService;
import com.carolin_violet.travel_system.service.TravelNoteService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/travel_system/travel-note")
@CrossOrigin
public class TravelNoteController {

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private TouristPhotosService touristPhotosService;

    // 分页查询游记
    @SystemControllerLog(operate = "分页查询游记",module = "游记管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTE')")
    @GetMapping("pageNote/{current}/{limit}")
    public R getPageNote(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType) {
        Page<TravelNote> travelNotePage = new Page<>(current, limit);

        QueryWrapper<TravelNote> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        travelNoteService.page(travelNotePage, wrapper);

        long total = travelNotePage.getTotal();

        List<TravelNote> records = travelNotePage.getRecords();

        List<TravelNoteVo> travelNoteVoList = new ArrayList<>();
        for (TravelNote travelNote: records) {
            TravelNoteVo travelNoteVo = new TravelNoteVo();
            BeanUtils.copyProperties(travelNote, travelNoteVo);
            List<String> photoList = new ArrayList<>();
            List<TouristPhotos> touristPhotos = touristPhotosService.list(new QueryWrapper<TouristPhotos>().eq("mark_id", travelNoteVo.getId()));
            for (TouristPhotos touristPhoto: touristPhotos) {
                photoList.add(touristPhoto.getUrl());
            }
            travelNoteVo.setPhotoUrlList(photoList);

            travelNoteVoList.add(travelNoteVo);
        }

        return R.ok().data("total", total).data("rows", travelNoteVoList);

    }

//     删除游记
@SystemControllerLog(operate = "删除游记",module = "游记管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTE')")
    @DeleteMapping("{id}")
    public R removeNote(@PathVariable String id) {
        touristPhotosService.removePhotos(id);
        travelNoteService.removeById(id);
        return R.ok();
    }
}
