package com.weimok.common.api.category.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-06-11:18
 */
@Data
public class CategoryVo implements Serializable {
    private Long id;

    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;
}
