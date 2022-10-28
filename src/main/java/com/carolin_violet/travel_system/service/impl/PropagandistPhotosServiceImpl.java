package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.*;
import com.carolin_violet.travel_system.bean.vo.PagePhotosVo;
import com.carolin_violet.travel_system.mapper.PropagandistPhotosMapper;
import com.carolin_violet.travel_system.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-18
 */
@Service
public class PropagandistPhotosServiceImpl extends ServiceImpl<PropagandistPhotosMapper, PropagandistPhotos> implements PropagandistPhotosService {

    @Autowired
    private OssService ossService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private DelicacyService delicacyService;

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Autowired
    private NoticeService noticeService;



    // 删除数据库图片数据前需要先删除阿里云上的图片
    @Override
    public void deletePhotoById(String id) {
        ossService.removeRemotePhoto(baseMapper.selectById(id).getUrl());
        baseMapper.deleteById(id);
    }

    // 根据标签查询列表数据
    @Override
    public List getMarkNames(String mark) {
        if (mark.equals("旅馆")) {
            return hotelService.list(new QueryWrapper<Hotel>().select("id", "name"));
        } else if (mark.equals("景点")) {
            return scenicSpotService.list(new QueryWrapper<ScenicSpot>().select("id", "name"));
        } else if (mark.equals("美食")) {
            return delicacyService.list(new QueryWrapper<Delicacy>().select("id", "name"));
        } else if (mark.equals("公告")) {
            return noticeService.list(new QueryWrapper<Notice>().select("id", "name"));
        }
        return new ArrayList();
    }

    // 添加图片
    @Override
    public PropagandistPhotos addPhoto(String mark, String markId, MultipartFile file) {
        String url = ossService.uploadFileAvatar(file, "travel_GaoChun/propagandist/photos/" + mark);
        PropagandistPhotos propagandistPhoto = new PropagandistPhotos();
        propagandistPhoto.setMark(mark);
        propagandistPhoto.setMarkId(markId);
        propagandistPhoto.setUrl(url);
        baseMapper.insert(propagandistPhoto);
        return propagandistPhoto;
    }

    // 图库中模糊查询图片
    @Override
    public List<PropagandistPhotos> getPhotos(String mark, String name) {
        List<PropagandistPhotos> list = new ArrayList<>();
        if (mark.equals("")) {
            if (name.equals("")) {
                return baseMapper.selectList(null);
            } else {
                List<Hotel> hotelList = hotelService.list(new QueryWrapper<Hotel>().like("name", name));
                List<Delicacy> delicacyList = delicacyService.list(new QueryWrapper<Delicacy>().like("name", name));
                List<ScenicSpot> scenicSpotList = scenicSpotService.list(new QueryWrapper<ScenicSpot>().like("name", name));
                List<Notice> noticeList = noticeService.list(new QueryWrapper<Notice>().like("name", name));

                for (Hotel hotel: hotelList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", hotel.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
                for (Delicacy delicacy: delicacyList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", delicacy.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
                for (ScenicSpot scenicSpot: scenicSpotList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", scenicSpot.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
                for (Notice notice: noticeList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", notice.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
                return list;
            }

        } else {
            if (mark.equals("旅馆")) {
                List<Hotel> hotelList = hotelService.list(new QueryWrapper<Hotel>().like("name", name));
                for (Hotel hotel: hotelList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", hotel.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
            } else if (mark.equals("美食")) {
                List<Delicacy> delicacyList = delicacyService.list(new QueryWrapper<Delicacy>().like("name", name));
                for (Delicacy delicacy: delicacyList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", delicacy.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
            } else if (mark.equals("景点")) {
                List<ScenicSpot> scenicSpotList = scenicSpotService.list(new QueryWrapper<ScenicSpot>().like("name", name));
                for (ScenicSpot scenicSpot: scenicSpotList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", scenicSpot.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
            } else if (mark.equals("公告")) {
                List<Notice> noticeList = noticeService.list(new QueryWrapper<Notice>().like("name", name));
                for (Notice notice: noticeList) {
                    List<PropagandistPhotos> propagandistPhotosList = baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark_id", notice.getId()));
                    for (int i = 0; i < propagandistPhotosList.size(); i++) {
                        list.add(propagandistPhotosList.get(i));
                    }
                }
            } else if (mark.equals("其他")) {
                return baseMapper.selectList(new QueryWrapper<PropagandistPhotos>().eq("mark", mark));
            }
            return list;
        }
    }

    // 根据id分页查询图片
    @Override
    public PagePhotosVo getPhotosByMarkId(String id, Long current, Long limit) {
        System.out.println(current);
        System.out.println(limit);
        Page<PropagandistPhotos> propagandistPhotosPage = new Page<>(current, limit);
        QueryWrapper<PropagandistPhotos> wrapper = new QueryWrapper<>();
        wrapper.eq("mark_id", id).orderByDesc("create_time");

        baseMapper.selectPage(propagandistPhotosPage, wrapper);
        PagePhotosVo pagePhotosVo = new PagePhotosVo();
        pagePhotosVo.setPhotoList(propagandistPhotosPage.getRecords());
        pagePhotosVo.setTotal(propagandistPhotosPage.getTotal());
        return pagePhotosVo;
    }

}
