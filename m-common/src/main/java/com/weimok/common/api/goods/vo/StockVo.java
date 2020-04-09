package com.weimok.common.api.goods.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-05-10:21
 */
@Data
public class StockVo implements Serializable {

    private Long skuId;

    private Integer stock;
}
