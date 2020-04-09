package com.weimok.common.api.goods.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-08-15:41
 */
@Data
public class SpecGroupVo implements Serializable {
    private Long id;

    private Long cid;

    private String name;

    @Transient
    private List<SpecParamVo> paramVos;
}
