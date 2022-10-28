package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.TravelNote;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carolin_violet.travel_system.bean.vo.TravelNoteVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
public interface TravelNoteService extends IService<TravelNote> {

    void addNote(TravelNoteVo travelNoteVo);
}
