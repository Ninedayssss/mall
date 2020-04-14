package com.weimok.goods.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-05-9:37
 */
@Data
@Table(name = "m_spu")
public class Spu {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long brandId;
    private Long cid1;//1级类目
    private Long cid2;//2级类目
    private Long cid3;//3级类目

    private String title;   //标题
    private Long price;
    private Boolean saleable;   //是否上架  true1为上架 false0为下架
    private Boolean valid;      //是否有效，逻辑删除使用   true1为有效 false0为无效

    @Transient
    private String cname;

    @Transient
    private String bname;


}
