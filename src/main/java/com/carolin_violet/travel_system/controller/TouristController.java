package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Delicacy;
import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.bean.Tourist;
import com.carolin_violet.travel_system.bean.vo.FeedbackVo;
import com.carolin_violet.travel_system.bean.vo.TravelNoteVo;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.FeedbackService;
import com.carolin_violet.travel_system.service.OssService;
import com.carolin_violet.travel_system.service.TouristService;
import com.carolin_violet.travel_system.service.TravelNoteService;
import com.carolin_violet.travel_system.utils.JwtUtils;
import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/travel_system/tourist")
@CrossOrigin
public class TouristController {

    @Autowired
    private TouristService touristService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 分页查询游客
    @SystemControllerLog(operate = "分页查询游客",module = "游客管理")
    @GetMapping("{cur}/{limit}")
    @PreAuthorize("hasAnyAuthority('ROLE_TOURIST')")
    public R findAllTourist(@PathVariable long cur, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey, @RequestParam String genderType) {
        Page<Tourist> touristPage = new Page<>(cur, limit);
        QueryWrapper<Tourist> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        if (!genderType.equals("")) wrapper.like("gender", genderType);
        if (!searchKey.equals("")) wrapper.like("nickName", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        touristService.page(touristPage, wrapper);

        long total = touristPage.getTotal();
        List<Tourist> records = touristPage.getRecords();
        return R.ok().data("rows", records).data("total", total);
    }

    // 修改游客密码
    @SystemControllerLog(operate = "修改游客密码",module = "游客管理")
    @PreAuthorize("hasAnyAuthority('ROLE_TOURIST')")
    @PatchMapping("changePassword")
    public R changePassword(HttpServletRequest request, @RequestBody Tourist tourist) {

        String privateKey = (String) redisTemplate.opsForValue().get(request.getRemoteAddr() + "-privateKey:");
        try {
            // RSA解密密码
            String decryptPassword = RsaUtils.decrypt(tourist.getPassword(), RsaUtils.getPrivateKey(privateKey));
            // 用Bcrypt对密码加密
            tourist.setPassword(new BCryptPasswordEncoder().encode(decryptPassword));
            boolean flag = touristService.updateById(tourist);
            if (flag) return R.ok();
            else return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    // 逻辑删除游客
    @SystemControllerLog(operate = "删除游客",module = "游客管理")
    @PreAuthorize("hasAnyAuthority('ROLE_TOURIST')")
    @DeleteMapping("{id}")
    public R removeTourist(@PathVariable String id) {
        // 先删除所有附属数据
        touristService.removeAll(id);
        // 删除游客
        boolean flag = touristService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

