package com.weimok.search.service;

import com.weimok.common.api.brand.BrandService;
import com.weimok.common.api.brand.vo.BrandVo;
import com.weimok.common.api.category.CategoryService;
import com.weimok.common.api.category.vo.CategoryVo;
import com.weimok.common.api.goods.GoodsService;
import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.search.api.PageService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itsNine
 * @create 2020-04-10-17:01
 */
@Service(version = "1.0.0")
public class PageServiceImpl implements PageService {

    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    @Reference(version = "1.0.0")
    private BrandService brandService;

    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    @Override
    public Map<String, Object> loadModel(Long spuId) {
        HashMap<String, Object> model = new HashMap<>();

        //查询spu
        SpuVo spuVo = goodsService.querySpuById(spuId);

        //查询品牌
        BrandVo brandVo = brandService.queryById(spuVo.getBrandId());

        //查询商品分类
        List<CategoryVo> categoryVos = categoryService.queryByIds(Arrays.asList(spuVo.getCid1(), spuVo.getCid2(), spuVo.getCid3()));

        model.put("title",spuVo.getTitle());
        model.put("id",spuVo.getId());
        model.put("brand",brandVo);
        model.put("categories",categoryVos);
        model.put("price",spuVo.getPrice());

        return model;
    }
}
