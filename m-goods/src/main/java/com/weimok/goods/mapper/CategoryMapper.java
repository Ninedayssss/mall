package com.weimok.goods.mapper;

import com.weimok.goods.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author itsNine
 * @create 2020-04-06-11:16
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
}
