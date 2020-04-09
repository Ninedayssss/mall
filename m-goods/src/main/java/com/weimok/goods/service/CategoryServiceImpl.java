package com.weimok.goods.service;

import com.weimok.common.api.category.CategoryService;
import com.weimok.common.api.category.vo.CategoryVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.common.exception.MallException;
import com.weimok.goods.mapper.CategoryMapper;
import com.weimok.goods.pojo.Category;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-11:22
 */
@Service(version = "1.0.0")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点id查询分类集合
     * @param pid
     * @return
     */
    @Override
    public List<CategoryVo> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<CategoryVo> CategoryVos = new ArrayList<>();

        for (Category category1 : list) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category1.getId());
            categoryVo.setIsParent(category1.getIsParent());
            categoryVo.setName(category1.getName());
            categoryVo.setParentId(category1.getParentId());
            categoryVo.setSort(category1.getSort());
            CategoryVos.add(categoryVo);

        }

        return CategoryVos;
    }

    /**
     * 根据id集合查询分类集合
     * @param ids
     * @return
     */
    @Override
    public List<CategoryVo> queryByIds(List<Long> ids) {
        List<Category> list = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<CategoryVo> CategoryVos = new ArrayList<>();
        for (Category category : list) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setName(category.getName());
            categoryVo.setIsParent(category.getIsParent());
            categoryVo.setParentId(category.getParentId());
            categoryVo.setSort(category.getSort());
            CategoryVos.add(categoryVo);
        }
        return CategoryVos;
    }
}
