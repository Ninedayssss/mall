package com.weimok.goods.service;

import com.weimok.common.api.goods.SpecificationService;
import com.weimok.common.api.goods.vo.SpecGroupVo;
import com.weimok.common.api.goods.vo.SpecParamVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.common.exception.MallException;
import com.weimok.goods.mapper.SpecGroupMapper;
import com.weimok.goods.mapper.SpecParamMapper;
import com.weimok.goods.pojo.SpecGroup;
import com.weimok.goods.pojo.SpecParam;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-08-16:14
 */
@Service(version = "1.0.0")
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroupVo> queryGroupByCid(Long cid) {
        //设置查询条件
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        //查询
        List<SpecGroup> list = groupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        List<SpecGroupVo> specGroupVos = new ArrayList<>();
        for (SpecGroup group : list) {
            SpecGroupVo specGroupVo = new SpecGroupVo();
            specGroupVo.setCid(group.getCid());
            specGroupVo.setName(group.getName());
            specGroupVo.setId(group.getId());
            specGroupVos.add(specGroupVo);
        }
        return specGroupVos;
    }

    /**
     * 根据groupId和分类id查询具体参数信息
     * @param gid
     * @param cid
     * @return
     */
    @Override
    public List<SpecParamVo> queryParamList(Long gid, Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGroupId(gid);

        List<SpecParam> list = paramMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        List<SpecParamVo> specParamVos = new ArrayList<>();
        for (SpecParam param : list) {
            SpecParamVo specParamVo = new SpecParamVo();
            specParamVo.setCid(param.getCid());
            specParamVo.setGeneric(param.getGeneric());
            specParamVo.setGroupId(param.getGroupId());
            specParamVo.setId(param.getId());
            specParamVo.setName(param.getName());
            specParamVo.setNumeric(param.getNumeric());
            specParamVo.setSearching(param.getSearching());
            specParamVo.setSegments(param.getSegments());
            specParamVo.setUnit(param.getUnit());

            specParamVos.add(specParamVo);
        }
        return specParamVos;
    }

    /**
     * 保存规格组
     * @param specGroupVo
     */
    @Override
    public void saveSpecGroup(SpecGroupVo specGroupVo) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(specGroupVo.getCid());
        specGroup.setId(specGroupVo.getId());
        specGroup.setName(specGroupVo.getName());
        int count = groupMapper.insert(specGroup);
        if (count != 1){
            throw new MallException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    /**
     * 删除规格组
     * @param id
     */
    @Override
    public void deleteSpecGroup(Long id) {
        if (id == null){
            throw new MallException(ExceptionEnum.INVALID_PARAM);
        }
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int count = groupMapper.deleteByPrimaryKey(specGroup);
        if (count != 1){
            throw new MallException(ExceptionEnum.DELETE_SPEC_GROUP_FAILED);
        }

    }

    /**
     * 修改规格组
     * @param specGroupVo
     */
    @Override
    public void updateSpecGroup(SpecGroupVo specGroupVo) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(specGroupVo.getCid());
        specGroup.setId(specGroupVo.getId());
        specGroup.setName(specGroupVo.getName());
        int count = groupMapper.updateByPrimaryKey(specGroup);
        if (count != 1){
            throw new MallException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }

    /**
     * 保存具体参数
     * @param specParamVo
     */
    @Override
    public void saveSpecParam(SpecParamVo specParamVo) {
        SpecParam specParam = new SpecParam();
         specParam.setGroupId(specParamVo.getGroupId());
         specParam.setCid(specParamVo.getCid());
         specParam.setGeneric(specParamVo.getGeneric());
         specParam.setId(specParamVo.getId());
         specParam.setName(specParamVo.getName());
         specParam.setNumeric(specParamVo.getNumeric());
         specParam.setSearching(specParamVo.getSearching());
         specParam.setSegments(specParamVo.getSegments());
         specParam.setUnit(specParamVo.getUnit());

        int count = paramMapper.insert(specParam);
        if (count != 1){
            throw new MallException(ExceptionEnum.SPEC_PARAM_CREATED_FAILED);
        }

    }

    /**
     * 删除具体参数
     * @param id
     */
    @Override
    public void deleteSpecParam(Long id) {
        if (id == null){
            throw new MallException(ExceptionEnum.INVALID_PARAM);
        }
        int count = paramMapper.deleteByPrimaryKey(id);
        if(count != 1){
            throw new MallException(ExceptionEnum.DELETE_SPEC_PARAM_FAILED);
        }
    }

    /**
     * 修改具体参数
     * @param specParamVo
     */
    @Override
    public void updateSpecParam(SpecParamVo specParamVo) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(specParamVo.getGroupId());
        specParam.setCid(specParamVo.getCid());
        specParam.setGeneric(specParamVo.getGeneric());
        specParam.setId(specParamVo.getId());
        specParam.setName(specParamVo.getName());
        specParam.setNumeric(specParamVo.getNumeric());
        specParam.setSearching(specParamVo.getSearching());
        specParam.setSegments(specParamVo.getSegments());
        specParam.setUnit(specParamVo.getUnit());
        int count = paramMapper.updateByPrimaryKeySelective(specParam);
        if(count != 1){
            throw new MallException(ExceptionEnum.UPDATE_SPEC_PARAM_FAILED);
        }
    }

    /**
     * 根据分类id查询规格组及组内参数
     * @param cid 分类id
     * @return
     */
    @Override
    public List<SpecGroupVo> queryListByCid(Long cid) {
        //查询规格组
        List<SpecGroupVo> specGroupVos = queryGroupByCid(cid);
        //查询当前分类下的具体参数
        List<SpecParamVo> specParamVos = queryParamList(null, cid);

        //把规格参数变成map，key是规格组的id，map是组下的所有参数
        HashMap<Long,List<SpecParamVo>> map = new HashMap<>();
        for (SpecParamVo specParamVo : specParamVos) {
            if (!map.containsKey(specParamVo.getGroupId())){
                //组id在map中不存在，新增一个list
                map.put(specParamVo.getGroupId(),new ArrayList<>());
            }
            map.get(specParamVo.getGroupId()).add(specParamVo);
        }

        //填充具体参数到规格组中
        for (SpecGroupVo specGroupVo : specGroupVos) {
            specGroupVo.setParamVos(map.get(specGroupVo.getId()));
        }

        return specGroupVos;
    }
}
