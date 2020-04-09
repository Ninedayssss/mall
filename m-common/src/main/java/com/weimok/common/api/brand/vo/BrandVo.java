package com.weimok.common.api.brand.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-06-9:38
 */
@Data
public class BrandVo implements Serializable {
    private Long id;
    private String name;    //品牌名称
    private String image;   //图片
    private Character letter;//品牌首字母
}
