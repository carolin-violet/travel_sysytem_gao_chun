package com.carolin_violet.travel_system.service.impl;

import com.carolin_violet.travel_system.bean.Comment;
import com.carolin_violet.travel_system.mapper.CommentMapper;
import com.carolin_violet.travel_system.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
