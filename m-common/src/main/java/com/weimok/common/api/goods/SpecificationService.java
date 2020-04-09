package com.weimok.common.api.goods;

import com.weimok.common.api.goods.vo.SpecGroupVo;
import com.weimok.common.api.goods.vo.SpecParamVo;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-08-15:43
 */
public interface SpecificationService {

    //根据分类id查询参数组
    List<SpecGroupVo> queryGroupByCid(Long cid);

    //根据分组id和分类id查询具体参数
    List<SpecParamVo> queryParamList(Long gid,Long cid);

    //保存参数组
    void saveSpecGroup(SpecGroupVo specGroupVo);

    //删除参数组
    void deleteSpecGroup(Long id);

    //修改参数组
    void updateSpecGroup(SpecGroupVo specGroupVo);

    //保存参数组下的具体参数
    void saveSpecParam(SpecParamVo specParamVo);

    //删除具体参数
    void deleteSpecParam(Long id);

    //修改具体参数
    void updateSpecParam(SpecParamVo specParamVo);

    //查询包含具体参数信息的参数组
    List<SpecGroupVo> queryListByCid(Long cid);
}
