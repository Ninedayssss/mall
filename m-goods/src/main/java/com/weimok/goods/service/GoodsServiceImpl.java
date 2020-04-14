package com.weimok.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weimok.common.api.category.vo.CategoryVo;
import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.goods.mapper.SpuMapper;
import com.weimok.common.api.goods.GoodsService;
import com.weimok.common.exception.MallException;
import com.weimok.common.vo.PageResult;
import com.weimok.goods.pojo.Spu;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itsNine
 * @create 2020-04-05-11:10
 */
@Service(version = "1.0.0")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private BrandServiceImpl brandService;


    /**
     * 添加Spu
     * @param spuVo
     */
    public void saveGoods(SpuVo spuVo) {
        //新增spu
        Spu spu = new Spu();
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setBrandId(spuVo.getBrandId());
        spu.setPrice(spuVo.getPrice());
        spu.setTitle(spuVo.getTitle());

        System.out.println("价格"+spu.getPrice());


        int count = spuMapper.insert(spu);
        if (count != 1){
            throw new MallException(ExceptionEnum.GOODS_SAVE_ERROR);
        }


    }



    /**
     * 商品下架
     * @param id
     */
    public void goodsSoldOut(Long id) {
        //根据id查找出spu
        Spu oldSpu = spuMapper.selectByPrimaryKey(id);
        //设置下架
        oldSpu.setSaleable(false);
        //更新
        spuMapper.updateByPrimaryKeySelective(oldSpu);
    }

    /**
     * 商品上架
     * @param id
     */
    public void goodsSoldUp(Long id) {
        Spu oldSpu = spuMapper.selectByPrimaryKey(id);
        oldSpu.setSaleable(true);
        spuMapper.updateByPrimaryKeySelective(oldSpu);
    }

    /**
     * 逻辑删除商品
     * @param id
     */
    public void deleteGoods(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setValid(false);
        spuMapper.updateByPrimaryKeySelective(spu);

    }

    /**
     * 修改商品
     * @param spuVo
     */
    public void updateGoods(SpuVo spuVo) {
        if (spuVo.getId() == null){
            throw new MallException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }

        //修改spu
        Spu spu = new Spu();
        spu.setId(spuVo.getId());
        spu.setValid(spuVo.getValid());
        spu.setSaleable(spuVo.getSaleable());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setPrice(spuVo.getPrice());
        spu.setBrandId(spuVo.getBrandId());
        spu.setTitle(spuVo.getTitle());

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1){
            throw new MallException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }


    }

    /**
     * 分页查询Spu
     * @param page
     * @param rows
     * @param salebale
     * @param key
     * @param valid
     * @return
     */
    public PageResult<SpuVo> querySpuByPage(Integer page, Integer rows, Boolean salebale, String key, Boolean valid) {
        //分页
        PageHelper.startPage(page,rows);

        //过滤条件设置
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索字段过滤
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if (salebale != null){
            criteria.andEqualTo("saleable",salebale);
        }
        //是否删除过滤
        criteria.andEqualTo("valid",valid);

        //设置排序
        example.setOrderByClause("id DESC");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);

        List<SpuVo> spuVos = new ArrayList<>();
        for (Spu spu : spus) {
            SpuVo spuVo = new SpuVo();
            spuVo.setId(spu.getId());
            spuVo.setSaleable(spu.getSaleable());
            spuVo.setTitle(spu.getTitle());
            spuVo.setValid(spu.getValid());
            spuVo.setCid1(spu.getCid1());
            spuVo.setCid2(spu.getCid2());
            spuVo.setCid3(spu.getCid3());
            spuVo.setPrice(spu.getPrice());
            spuVo.setBrandId(spu.getBrandId());
            spuVos.add(spuVo);
        }

        //处理品牌和分类名称
        loadCategoryAndBrandName(spuVos);


        //解析分页结果
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        PageInfo<SpuVo> info = new PageInfo<>(spuVos);

        return new PageResult<>(spuPageInfo.getTotal(),spuVos);

    }

    /**
     * 处理分类和品牌名称
     * @param spuVos
     */
    private void loadCategoryAndBrandName(List<SpuVo> spuVos){
        for (SpuVo spuVo : spuVos) {
            //处理分类名称
            List<String> categoryNames = categoryService.queryByIds(Arrays.asList(spuVo.getCid1(), spuVo.getCid2(), spuVo.getCid3()))
                    .stream().map(CategoryVo::getName).collect(Collectors.toList());
            spuVo.setCname(StringUtils.join(categoryNames,"/"));
            //处理品牌名称
            spuVo.setBname(brandService.queryById(spuVo.getBrandId()).getName());
        }
    }



    /**
     * 根据id查询spu
     * @param id
     * @return
     */
    public SpuVo querySpuById(Long id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null){
            throw new MallException(ExceptionEnum.GOODS_NOT_FOND);
        }
        SpuVo spuVo = new SpuVo();
        spuVo.setValid(spu.getValid());
        spuVo.setTitle(spu.getTitle());
        spuVo.setSaleable(spu.getSaleable());
        spuVo.setCid1(spu.getCid1());
        spuVo.setCid2(spu.getCid2());
        spuVo.setCid3(spu.getCid3());
        spuVo.setBrandId(spu.getBrandId());
        spuVo.setPrice(spu.getPrice());
        spuVo.setId(spu.getId());

        return spuVo;
    }

    /**
     * 根据spu的id集合查询spu列表
     * @param ids
     * @return
     */
    @Override
    public List<SpuVo> querySpuListByIds(List<Long> ids) {
        List<Spu> spus = spuMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(spus)){
            throw new MallException(ExceptionEnum.GOODS_NOT_FOND);
        }
        List<SpuVo> spuVos = new ArrayList<>();
        for (Spu spu : spus) {
            SpuVo spuVo = new SpuVo();
            spuVo.setValid(spu.getValid());
            spuVo.setTitle(spu.getTitle());
            spuVo.setSaleable(spu.getSaleable());
            spuVo.setCid1(spu.getCid1());
            spuVo.setCid2(spu.getCid2());
            spuVo.setCid3(spu.getCid3());
            spuVo.setBrandId(spu.getBrandId());
            spuVo.setPrice(spu.getPrice());
            spuVo.setId(spu.getId());

            spuVos.add(spuVo);
        }
        return spuVos;
    }

}
