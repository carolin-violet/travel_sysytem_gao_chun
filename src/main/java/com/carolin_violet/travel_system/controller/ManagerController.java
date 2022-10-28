package com.carolin_violet.travel_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.bean.security.SecurityUser;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.service.ManagerService;
import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName ManagerInfoController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/22 9:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/travel_system/manager")
@CrossOrigin
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 查询所有管理员信息
    @SystemControllerLog(operate = "分页查询管理员",module = "管理员管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @GetMapping("{current}/{limit}")
    public R findAllManager(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey) {
        Page<Manager> managerPage = new Page<>(current, limit);
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        managerService.page(managerPage, wrapper);

        List<Manager> records = managerPage.getRecords();
        long total = managerPage.getTotal();

        return R.ok().data("items", records).data("total", total);
    }

    // 添加管理员信息
//    @SystemControllerLog(operate = "添加管理员",module = "管理员管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @PostMapping("addManager")
    public R addManager(HttpServletRequest request, @RequestBody Manager manager) {

        String privateKey = (String) redisTemplate.opsForValue().get(request.getRemoteAddr() + "-privateKey:");

        try {
            // RSA对吗密码进行解密
            String decryptPassword = RsaUtils.decrypt(manager.getPassword(), RsaUtils.getPrivateKey(privateKey));
            // 用Bcrypt对密码加密
            manager.setPassword(new BCryptPasswordEncoder().encode(decryptPassword));
            boolean save = managerService.save(manager);
            if (save) {
                return R.ok();
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    // 修改管理员信息
    @SystemControllerLog(operate = "修改管理员信息",module = "管理员管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @PutMapping("updateManager")
    public R updateManager(@RequestBody Manager manager) {
        boolean flag = managerService.updateById(manager);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 修改管理员密码
    @SystemControllerLog(operate = "修改管理员密码",module = "管理员管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @PatchMapping("changePassword")
    public R changePassword(HttpServletRequest request, @RequestBody Manager manager) {

        String privateKey = (String) redisTemplate.opsForValue().get(request.getRemoteAddr() + "-privateKey:");

        // RSA解密密码
        try {
            String decryptPassword = RsaUtils.decrypt(manager.getPassword(), RsaUtils.getPrivateKey(privateKey));
            // 用Bcrypt对密码加密
            manager.setPassword(new BCryptPasswordEncoder().encode(decryptPassword));
            boolean flag = managerService.updateById(manager);
            if (flag) {
                return R.ok();
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    // 逻辑删除管理员
    @SystemControllerLog(operate = "删除管理员",module = "管理员管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @DeleteMapping("{id}")
    public R removeManger(@PathVariable String id) {
        boolean flag = managerService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 登录后获取管理员信息
    @SystemControllerLog(operate = "登录获取管理员个人信息",module = "管理员管理")
    @GetMapping("getInfo")
    public R getInfo(@RequestParam String token) {
        if (token != null) {
            //从token获取手机号
            String username = new TokenManager().getUserFromToken(token);
             SecurityUser user = (SecurityUser) redisTemplate.opsForValue().get("securityUser:" + username);
            QueryWrapper<Manager> wrapper = new QueryWrapper<>();
            wrapper.eq("telephone", username);
            Manager manager = managerService.getOne(wrapper);
            return R.ok().data("info", manager).data("roles", user.getPermissionValueList());
        } else {
            return R.error().message("获取信息失败");
        }
    }

}
