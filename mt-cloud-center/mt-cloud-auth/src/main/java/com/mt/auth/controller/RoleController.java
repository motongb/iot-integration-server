package com.mt.auth.controller;

import com.mt.auth.service.RoleService;
import com.mt.common.entity.auth.RoleEntity;
import com.mt.common.http.HttpResult;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author motb
 * @date 2020/4/14 14:38
 * @description
 */
@Api(tags = "系统-角色")
@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping
    public HttpResult get() {
        return HttpResult.success(roleService.selectAll());
    }

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
    public HttpResult batchDelete(@RequestBody List<Long> ids) {
        roleService.batchDelete(ids);
        return HttpResult.success();
    }
}
