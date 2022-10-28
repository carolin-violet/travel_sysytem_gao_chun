package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.PropagandistPhotos;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carolin_violet.travel_system.bean.vo.PagePhotosVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-18
 */
public interface PropagandistPhotosService extends IService<PropagandistPhotos> {
    
    

    void deletePhotoById(String id);

    List getMarkNames(String mark);

    PropagandistPhotos addPhoto(String mark, String markId, MultipartFile file);

    List<PropagandistPhotos> getPhotos(String mark, String name);

    PagePhotosVo getPhotosByMarkId(String id, Long current, Long limit);
}
