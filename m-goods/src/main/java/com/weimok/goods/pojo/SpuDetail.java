package com.weimok.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author itsNine
 * @create 2020-04-08-15:25
 */
@Data
@Table(name = "m_spu_detail")
public class SpuDetail {

    @Id
    private Long spuId;//对应的spu的id
    private String description;//商品描述
    private String specialSpec;//商品特殊规格的名称及可选值模板
    private String genericSpec;//商品的全局规格属性
    private String packingList;//包装清单
    private String afterService;//售后服务
}