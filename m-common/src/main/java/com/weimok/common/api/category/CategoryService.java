package com.weimok.common.api.category;

import com.weimok.common.api.category.vo.CategoryVo;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-11:17
 */
public interface CategoryService {
    //根据父节点id查询分类集合
    List<CategoryVo> queryCategoryByPid(Long pid);

    //根据id集合查询分类集合
    List<CategoryVo> queryByIds(List<Long> ids);


}
