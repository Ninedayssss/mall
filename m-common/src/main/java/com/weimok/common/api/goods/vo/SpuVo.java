package com.weimok.common.api.goods.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-05-10:20
 */
@Data
public class SpuVo implements Serializable {
    private Long id;
    private Long brandId;
    private Long cid1;//1级类目
    private Long cid2;//2级类目
    private Long cid3;//3级类目
    private String title;   //标题
    private Long price;
    private Boolean saleable;   //是否上架
    private Boolean valid;      //是否有效，逻辑删除使用

    @Transient
    private String cname;

    @Transient
    private String bname;



}
