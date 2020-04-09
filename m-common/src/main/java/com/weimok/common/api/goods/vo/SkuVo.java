package com.weimok.common.api.goods.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-05-10:21
 */
@Data
public class SkuVo implements Serializable {
    private Long id;

    private Long spuId;
    private String title;
    private Long price;     //价格
    private String ownSpec; //sku特有规格参数
    private Boolean enable;     //是否有效，逻辑删除使用

    private Integer stock;      //库存
}
