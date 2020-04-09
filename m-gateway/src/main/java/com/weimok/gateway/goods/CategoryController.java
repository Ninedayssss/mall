package com.weimok.gateway.goods;

import com.weimok.common.api.category.CategoryService;
import com.weimok.common.api.category.vo.CategoryVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-11:29
 */
@RestController
@RequestMapping("/item/category")
public class CategoryController {
    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    /**
     * 根据父节点id查询商品分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<CategoryVo>> queryCategoryListByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok(categoryService.queryCategoryByPid(pid));
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<CategoryVo>> queryCategoryByIds(@RequestParam("ids")List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}
