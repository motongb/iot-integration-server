package com.mt.common.entity.sys;

import com.mt.common.core.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * @auther: motb
 * @date: 2020/4/14 16:10
 * @description:
 */
@Data
@Table(name = "sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class RoleRelMenuEntity extends BaseEntity {

    @ApiModelProperty(value = "角色id", example = "0")
    private Long roleId;

    @ApiModelProperty(value = "菜单id", example = "0")
    private Long menuId;
}
