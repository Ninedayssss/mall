package com.weimok.gateway.goods;

import com.weimok.common.api.goods.GoodsService;
import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.vo.PageResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itsNine
 * @create 2020-04-05-14:33
 */
@RestController
@RequestMapping("/item/")
public class GoodsController {
    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    /**
     * 分页查询SPU
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @param valid
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuVo>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "valid") Boolean valid
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key,valid));
    }

    /**
     * 商品新增
     * @param spuVo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuVo spuVo){
        goodsService.saveGoods(spuVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 商品修改
     * @param spuVo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuVo spuVo){
        goodsService.updateGoods(spuVo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




    /**
     * 根据spuid查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<SpuVo> querySpuById(@PathVariable("id")Long id){
        return ResponseEntity.ok(goodsService.querySpuById(id));
    }

    /**
     * 商品下架
     * @param id
     * @return
     */
    @PutMapping("spu/out/{id}")
    public ResponseEntity<Void> goodsSoldOut(@PathVariable("id")Long id){
        goodsService.goodsSoldOut(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 商品上架
     * @param id
     * @return
     */
    @PutMapping("spu/up/{id}")
    public ResponseEntity<Void> goodsSoldUp(@PathVariable("id")Long id){
        goodsService.goodsSoldUp(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 商品逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long id){
        goodsService.deleteGoods(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
