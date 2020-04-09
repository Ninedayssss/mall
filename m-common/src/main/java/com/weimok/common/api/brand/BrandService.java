package com.weimok.common.api.brand;

import com.weimok.common.api.brand.vo.BrandVo;
import com.weimok.common.vo.PageResult;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-9:38
 */
public interface BrandService {
    //分页查询品牌
    PageResult<BrandVo> queryBrandByPage(Integer page,Integer rows,String sortBy,Boolean desc,String key);

    //新增品牌
    void saveBrand(BrandVo brandVo, List<Long> cids);

    //根据id查询品牌
    BrandVo queryById(Long id);

    //根据分类id查询下面的品牌列表
    List<BrandVo> queryBrandByCid(Long cid);

    //根据id集合查询品牌列表
    List<BrandVo> queryByIds(List<Long> ids);

    //修改品牌
    void updateBrand(BrandVo brandVo,List<Long> cids);

    //删除品牌
    void deleteBrand(Long bid);

}
