package com.weimok.goods.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author itsNine
 * @create 2020-04-05-9:41
 */
@Data
@Table(name = "m_sku")
public class Sku {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long spuId;
    private String title;
    private Long price;     //价格
    private String ownSpec; //sku特有规格参数
    private Boolean enable;     //是否有效，逻辑删除使用 true为有效 false为无效

    @Transient
    private Integer stock;      //库存
}
