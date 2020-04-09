package com.weimok.common.api.goods.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-08-15:42
 */
@Data
public class SpuDetailVo implements Serializable {
    private Long spuId;//对应的spu的id
    private String description;//商品描述
    private String specialSpec;//商品特殊规格的名称及可选值模板
    private String genericSpec;//商品的全局规格属性
    private String packingList;//包装清单
    private String afterService;//售后服务
}
