package com.weimok.portal.search;

import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.vo.PageResult;
import com.weimok.search.api.SearchService;
import com.weimok.search.pojo.SearchRequest;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itsNine
 * @create 2020-04-10-15:49
 */
@RestController
@RequestMapping("/search/")
public class SearchController {
    @Reference(version = "1.0.0")
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<SpuVo>> search(@RequestBody SearchRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }
}
