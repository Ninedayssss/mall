package com.weimok.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weimok.common.api.brand.BrandService;
import com.weimok.common.api.brand.vo.BrandVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.goods.mapper.BrandMapper;
import com.weimok.common.exception.MallException;
import com.weimok.common.vo.PageResult;
import com.weimok.goods.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-9:45
 */
@Service(version = "1.0.0")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @Override
    public PageResult<BrandVo> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //设置分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)){
            //过滤条件设置
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序设置
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //System.out.println("brand"+list.size());

        List<BrandVo> BrandVos = new ArrayList<>();
        for (Brand brand : list) {
            BrandVo brandVo = new BrandVo();
            brandVo.setId(brand.getId());
            brandVo.setImage(brand.getImage());
            brandVo.setName(brand.getName());
            brandVo.setLetter(brand.getLetter());
            BrandVos.add(brandVo);
        }
        PageInfo<Brand> brandPageInfo = new PageInfo<>(list);
        PageInfo<BrandVo> info = new PageInfo<>(BrandVos);
        return new PageResult<>(brandPageInfo.getTotal(),BrandVos);
    }

    /**
     * 新增品牌
     * @param brandVo
     * @param cids  分类id集合
     */
    @Override
    @Transactional
    public void saveBrand(BrandVo brandVo, List<Long> cids) {
        //新增品牌
        Brand brand = new Brand();
        //brand.setId(null);
        brand.setImage(brandVo.getImage());
        brand.setName(brandVo.getName());
        brand.setLetter(brandVo.getLetter());
        int count = brandMapper.insert(brand);
        if (count != 1){
            throw new MallException(ExceptionEnum.BRAND_SAVE_ERROR);
        }

        //新增品牌与分类的中间表
        for (Long cid : cids){
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1){
                throw new MallException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }

    }

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    @Override
    public BrandVo queryById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null){
            throw new MallException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        BrandVo brandVo = new BrandVo();
        brandVo.setId(brand.getId());
        brandVo.setLetter(brand.getLetter());
        brandVo.setName(brand.getName());
        brandVo.setImage(brand.getImage());

        return brandVo;
    }

    /**
     * 根据分类id查找品牌集合
     * @param cid
     * @return
     */
    @Override
    public List<BrandVo> queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        List<BrandVo> brandVos = new ArrayList<>();

        for (Brand brand : list) {
            BrandVo brandVo = new BrandVo();
            brandVo.setId(brand.getId());
            brandVo.setLetter(brand.getLetter());
            brandVo.setName(brand.getName());
            brandVo.setImage(brand.getImage());

            brandVos.add(brandVo);
        }
        return brandVos;
    }

    /**
     * 根据id集合查找品牌列表
     * @param ids
     * @return
     */
    @Override
    public List<BrandVo> queryByIds(List<Long> ids) {
        List<Brand> list = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)){
            throw new MallException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        ArrayList<BrandVo> brandVos = new ArrayList<>();

        for (Brand brand : list) {
            BrandVo brandVo = new BrandVo();
            brandVo.setLetter(brand.getLetter());
            brandVo.setId(brand.getId());
            brandVo.setName(brand.getName());
            brandVo.setImage(brand.getImage());

            brandVos.add(brandVo);
        }
        return brandVos;
    }

    /**
     * 品牌修改
     * @param brandVo
     * @param cids
     */
    @Override
    @Transactional
    public void updateBrand(BrandVo brandVo, List<Long> cids) {
        Brand brand = new Brand();
        brand.setId(brandVo.getId());
        //System.out.println(brandVo.getLetter());
        brand.setLetter(brandVo.getLetter());
        brand.setName(brandVo.getName());
        brand.setImage(brandVo.getImage());
        //先删除中间表数据
        brandMapper.deleteByBrandIdInCategoryBrand(brand.getId());

        //修改品牌
        brandMapper.updateByPrimaryKey(brand);

        //插入中间表数据
        for (Long cid : cids) {
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }

    }

    /**
     * 删除品牌
     * @param bid
     */
    @Override
    public void deleteBrand(Long bid) {
        //维护中间表
        brandMapper.deleteByBrandIdInCategoryBrand(bid);
        //删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }
}
