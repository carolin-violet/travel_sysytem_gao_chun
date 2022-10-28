package com.carolin_violet.travel_system.bean.vo;

import com.carolin_violet.travel_system.bean.PropagandistPhotos;
import lombok.Data;

import java.util.List;

/**
 * @ClassName PhotosVo
 * @Description TODO
 * @Author zj
 * @Date 2022/8/18 21:50
 * @Version 1.0
 */
@Data
public class PagePhotosVo {
    private Long total;

    private List<PropagandistPhotos> photoList;
}
