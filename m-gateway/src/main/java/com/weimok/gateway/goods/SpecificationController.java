package com.weimok.gateway.goods;

import com.weimok.common.api.goods.SpecificationService;
import com.weimok.common.api.goods.vo.SpecGroupVo;
import com.weimok.common.api.goods.vo.SpecParamVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-08-16:51
 */
@RestController
@RequestMapping("/item/spec")
public class SpecificationController {
    @Reference(version = "1.0.0")
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroupVo>> queryGroupByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    /**
     * 查询参数集合
     * @param gid 规格组id
     * @param cid   分类id
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParamVo>> queryParamList(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid
    ){
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid));
    }

    /**
     * 新增商品规格组
     * @param specGroupVo
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroupVo specGroupVo){
        specificationService.saveSpecGroup(specGroupVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除规格组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id")Long id){
        specificationService.deleteSpecGroup(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改规格组
     * @param specGroupVo
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroupVo specGroupVo){
        specificationService.updateSpecGroup(specGroupVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 新增具体规格参数
     * @param specParamVo
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParamVo specParamVo){
        specificationService.saveSpecParam(specParamVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除具体规格参数
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id")Long id){
        specificationService.deleteSpecParam(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改规格参数
     * @param specParamVo
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParamVo specParamVo){
        specificationService.updateSpecParam(specParamVo);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("group")
    public ResponseEntity<List<SpecGroupVo>> queryListByCid(@RequestParam("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryListByCid(cid));
    }
}
