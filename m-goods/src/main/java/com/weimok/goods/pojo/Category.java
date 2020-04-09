package com.weimok.goods.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author itsNine
 * @create 2020-04-06-11:14
 */
@Data
@Table(name = "m_category")
public class Category {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private Long parentId;  //父节点id
    private Boolean isParent;   //是否为父节点 1是 0否
    private Integer sort;   //排序号
}
