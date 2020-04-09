package com.weimok.goods.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author itsNine
 * @create 2020-04-06-9:34
 */
@Data
@Table(name = "m_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;    //品牌名称
    private String image;   //图片
    private Character letter;//品牌首字母
}
