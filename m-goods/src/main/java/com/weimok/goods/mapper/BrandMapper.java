package com.weimok.goods.mapper;

import com.weimok.common.mapper.BaseMapper;
import com.weimok.goods.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-06-9:35
 */
public interface BrandMapper extends BaseMapper<Brand> {
    @Insert("INSERT INTO m_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Select("select b.* from m_category_brand cb inner join m_brand b on b.id = cb.brand_id where cb.category_id = #{cid}")
    List<Brand> queryByCategoryId(@Param("cid") Long cid);

    @Delete("DELETE FROM m_category_brand WHERE brand_id = #{bid}")
    void deleteByBrandIdInCategoryBrand(@Param("bid") Long bid);
}
