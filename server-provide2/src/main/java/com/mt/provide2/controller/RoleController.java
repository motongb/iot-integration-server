package com.mt.provide2.controller;

import com.mt.common.entity.sys.RoleEntity;
import com.mt.common.http.HttpResult;
import com.mt.provide2.service.RoleService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther: motb
 * @date: 2020/4/14 14:38
 * @description:
 */
@Api(tags = "系统-角色")
@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @PostMapping
    public HttpResult save(@RequestBody RoleEntity roleEntity) {
        roleService.save(roleEntity);
        return HttpResult.success();
    }

    @PutMapping
    public HttpResult update(@RequestBody RoleEntity roleEntity) {
        roleService.update(roleEntity);
        return HttpResult.success();
    }

    @DeleteMapping
    public HttpResult bachDelete(@RequestBody List<Long> ids) {
        return HttpResult.success();
    }
}
