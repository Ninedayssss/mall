package com.weimok.gateway.goods;

import com.weimok.common.api.brand.BrandService;
import com.weimok.common.api.brand.vo.BrandVo;
import com.weimok.common.vo.PageResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-10:29
 */
@RestController
@RequestMapping("/item/brand")
public class BrandController {
    @Reference(version = "1.0.0")
    private BrandService brandService;

    /**
     * 分页查询
     * @param page 页码
     * @param rows  每页内容
     * @param sortBy    根据呐列排序
     * @param desc      升降序
     * @param key       搜索关键词
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<BrandVo>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,key));
    }

    /**
     * 新增品牌
     * @param brandVo
     * @param cids  分类id
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(BrandVo brandVo, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brandVo,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询品牌列表
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<BrandVo>> queryBrandByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据品牌id查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<BrandVo> queryBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }

    /**
     * 根据id集合查询品牌列表
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<BrandVo>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.queryByIds(ids));
    }

    /**
     * 修改品牌
     * @param brandVo
     * @param cids 分类id
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(BrandVo brandVo,@RequestParam("cids")List<Long> cids){
        brandService.updateBrand(brandVo,cids);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 根据品牌id删除品牌
     * @param bid
     * @return
     */
    @DeleteMapping("bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid){
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
