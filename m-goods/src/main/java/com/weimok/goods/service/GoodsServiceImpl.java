package com.weimok.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weimok.common.api.category.vo.CategoryVo;
import com.weimok.common.api.goods.GoodsService;
import com.weimok.common.api.goods.vo.SkuVo;
import com.weimok.common.api.goods.vo.SpuDetailVo;
import com.weimok.common.api.goods.vo.SpuVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.common.exception.MallException;
import com.weimok.common.vo.PageResult;
import com.weimok.goods.mapper.SkuMapper;
import com.weimok.goods.mapper.SpuDetailMapper;
import com.weimok.goods.mapper.SpuMapper;
import com.weimok.goods.mapper.StockMapper;
import com.weimok.goods.pojo.Sku;
import com.weimok.goods.pojo.Spu;
import com.weimok.goods.pojo.SpuDetail;
import com.weimok.goods.pojo.Stock;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SpuDetailMapper detailMapper;

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
        spu.setTitle(spuVo.getTitle());
        spu.setSub_title(spuVo.getSub_title());


        int count = spuMapper.insert(spu);
        if (count != 1){
            throw new MallException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

      /*  //新增detail
        SpuDetailVo spuDetailVo = spuVo.getSpuDetailVo();
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetail.setAfterService(spuDetailVo.getAfterService());
        spuDetail.setDescription(spuDetailVo.getDescription());
        spuDetail.setGenericSpec(spuDetailVo.getGenericSpec());
        spuDetail.setPackingList(spuDetailVo.getPackingList());
        spuDetail.setSpecialSpec(spuDetailVo.getSpecialSpec());

        detailMapper.insert(spuDetail);*/

        //添加sku及库存
        //saveSkuAndStock(spuVo);

    }


    /**
     * 添加sku及库存
     * @param spuVo
     */
    public void saveSkuAndStock(SpuVo spuVo) {
        int count;  //定义库存集合
        List<Stock> stockList = new ArrayList<Stock>();

        //新增Sku
        List<SkuVo> skuVos = spuVo.getSkuVos();
        for (SkuVo skuVo : skuVos) {
            Sku sku = new Sku();
            sku.setSpuId(spuVo.getId());
            sku.setEnable(skuVo.getEnable());
            sku.setPrice(skuVo.getPrice());
            sku.setTitle(skuVo.getTitle());
            sku.setOwnSpec(skuVo.getOwnSpec());

            count = skuMapper.insert(sku);
            if (count != 1){
                throw new MallException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            //将单个sku的库存存入list
            Stock stock = new Stock();
            stock.setSkuId(skuVo.getId());
            stock.setStock(skuVo.getStock());
            stockList.add(stock);
        }

        //将所有sku的库存批量添加
        count = stockMapper.insertList(stockList);
        if (count != stockList.size()){
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

        List<Sku> skuList = spu.getSkus();
        for (Sku sku : skuList) {
            //遍历设置spu中的所有sku进行逻辑删除
            sku.setEnable(false);
        }
    }

    /**
     * 修改商品
     * @param spuVo
     */
    public void updateGoods(SpuVo spuVo) {
        if (spuVo.getId() == null){
            throw new MallException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }
        Sku sku = new Sku();
        sku.setSpuId(spuVo.getId());
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)){
            //删除sku
            skuMapper.delete(sku);
            //删除库存
            List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }

        //修改spu
        Spu spu = new Spu();
        spu.setId(spuVo.getId());
        spu.setValid(spuVo.getValid());
        spu.setSaleable(spuVo.getSaleable());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setBrandId(spuVo.getBrandId());

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1){
            throw new MallException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        //修改detail
        SpuDetailVo spuDetailVo = spuVo.getSpuDetailVo();
        SpuDetail spuDetail = new SpuDetail();

        spuDetail.setSpuId(spuVo.getId());
        spuDetail.setAfterService(spuDetailVo.getAfterService());
        spuDetail.setDescription(spuDetailVo.getDescription());
        spuDetail.setGenericSpec(spuDetailVo.getGenericSpec());
        spuDetail.setPackingList(spuDetailVo.getPackingList());
        spuDetail.setSpecialSpec(spuDetailVo.getSpecialSpec());

        count = detailMapper.updateByPrimaryKeySelective(spuDetail);
        if (count != 1){
            throw new MallException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        //修改sku和stock
        saveSkuAndStock(spuVo);

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
            spuVo.setSub_title(spu.getSub_title());
            spuVo.setTitle(spu.getTitle());
            spuVo.setValid(spu.getValid());
            spuVo.setCid1(spu.getCid1());
            spuVo.setCid2(spu.getCid2());
            spuVo.setCid3(spu.getCid3());
            spuVo.setBrandId(spu.getBrandId());
            //spuVo.setSkuVos(spu.getSkus());
            //设置spu下的sku
           /* List<SkuVo> skuVos = new ArrayList<>();
            List<Sku> skus = spu.getSkus();
            for (Sku sku : skus) {
                SkuVo skuVo = new SkuVo();
                skuVo.setId(sku.getId());
                skuVo.setEnable(sku.getEnable());
                skuVo.setPrice(sku.getPrice());
                skuVo.setSpuId(sku.getSpuId());
                skuVo.setStock(sku.getStock());
                skuVo.setTitle(sku.getTitle());

                skuVos.add(skuVo);
            }
            spuVo.setSkuVos(skuVos);*/
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
     * 根据spuid查询sku
     * @param spuId
     * @return
     */
    public List<SkuVo> querySkuBySpuId(Long spuId) {
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)){
            throw new MallException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }

        List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<SkuVo> skuVos = new ArrayList<>();
        for (Sku sku1 : skuList) {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(sku1.getId());
            skuVo.setTitle(sku1.getTitle());
            skuVo.setSpuId(sku1.getSpuId());
            skuVo.setPrice(sku1.getPrice());
            skuVo.setOwnSpec(sku1.getOwnSpec());
            skuVo.setEnable(sku1.getEnable());

            skuVos.add(skuVo);
        }
        //加载库存
        loadStockInSku(ids,skuVos);


        return skuVos;
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
        spuVo.setSkuVos(querySkuBySpuId(id));
        spuVo.setValid(spu.getValid());
        spuVo.setTitle(spu.getTitle());
        spuVo.setSub_title(spu.getSub_title());
        spuVo.setSaleable(spu.getSaleable());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setBrandId(spuVo.getBrandId());
        spuVo.setId(spu.getId());

        return spuVo;
    }

    /**
     * 查询sku的库存
     * @param ids
     * @param skuVos
     */
    @Override
    public void loadStockInSku(List<Long> ids, List<SkuVo> skuVos) {
        List<Stock> stockList = stockMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(stockList)){
            throw new MallException(ExceptionEnum.GOODS_STOCK_NOT_FOND);
        }
        //把stock变成一个map。key是sku的id，值是库存量
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        //将stockmap中的库存值插入到skuVos中
        skuVos.forEach(s -> s.setStock(stockMap.get(s.getId())));
    }

    /**
     * 根据skuid集合查询sku
     * @param ids
     * @return
     */
    @Override
    public List<SkuVo> querySkuByIds(List<Long> ids) {
        List<Sku> skus = skuMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(skus)){
            throw new MallException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        List<SkuVo> skuVos = new ArrayList<>();

        for (Sku sku : skus) {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(sku.getId());
            skuVo.setStock(sku.getStock());
            skuVo.setEnable(sku.getEnable());
            skuVo.setPrice(sku.getPrice());
            skuVo.setOwnSpec(sku.getOwnSpec());
            skuVo.setSpuId(sku.getSpuId());
            skuVo.setTitle(sku.getTitle());

            skuVos.add(skuVo);
        }
        return skuVos;
    }

    @Override
    public SpuDetailVo queryDetailById(Long spuId) {
        SpuDetail spuDetail = detailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null){
            throw new MallException(ExceptionEnum.GOODS_DETAIL_NOT_FOND);
        }
        SpuDetailVo spuDetailVo = new SpuDetailVo();
        spuDetailVo.setAfterService(spuDetail.getAfterService());
        spuDetailVo.setDescription(spuDetail.getDescription());
        spuDetailVo.setGenericSpec(spuDetail.getGenericSpec());
        spuDetailVo.setPackingList(spuDetail.getPackingList());
        spuDetailVo.setSpecialSpec(spuDetail.getSpecialSpec());
        spuDetailVo.setSpuId(spuDetail.getSpuId());

        return spuDetailVo;
    }
}
