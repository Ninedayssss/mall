package com.weimok.common.api.goods;

import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.vo.PageResult;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-05-10:20
 */
public interface GoodsService {
    //增加商品Spu
    void saveGoods(SpuVo spuVo);

    //下架商品
    void goodsSoldOut(Long id);

    //上架商品
    void goodsSoldUp(Long id);

    //删除商品
    void deleteGoods(Long id);

    //修改商品
    void updateGoods(SpuVo spuVo);

    //分页查找Spu
    PageResult<SpuVo> querySpuByPage(Integer page, Integer rows, Boolean salebale, String key, Boolean valid);


    //根据id查找Spu
    SpuVo querySpuById(Long id);

    //根据spu的id集合查询spu列表
    List<SpuVo> querySpuListByIds(List<Long> ids);


}
