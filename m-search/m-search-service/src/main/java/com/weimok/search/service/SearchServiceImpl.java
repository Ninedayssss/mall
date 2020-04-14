package com.weimok.search.service;

import com.weimok.common.api.goods.GoodsService;
import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.vo.PageResult;
import com.weimok.search.api.SearchService;
import com.weimok.search.pojo.SearchRequest;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author itsNine
 * @create 2020-04-10-15:36
 */
@Service(version = "1.0.0")
public class SearchServiceImpl implements SearchService {

    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    @Override
    public PageResult<SpuVo> search(SearchRequest request) {
        //搜索条件
        String key = request.getKey();
        int page = request.getPage() - 1;
        int size = request.getSize();

        PageResult<SpuVo> result = goodsService.querySpuByPage(page, size, true, key, true);

        return result;
    }
}
