package com.weimok.goods.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author itsNine
 * @create 2020-04-05-9:45
 */
@Data
@Table(name = "m_stock")
public class Stock {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long skuId;

    private Integer stock;
}
