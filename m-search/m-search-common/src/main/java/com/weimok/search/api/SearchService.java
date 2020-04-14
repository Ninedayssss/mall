package com.weimok.search.api;

import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.vo.PageResult;
import com.weimok.search.pojo.SearchRequest;

/**
 * @author itsNine
 * @create 2020-04-10-15:39
 */
public interface SearchService {
    PageResult<SpuVo> search(SearchRequest request);
}
