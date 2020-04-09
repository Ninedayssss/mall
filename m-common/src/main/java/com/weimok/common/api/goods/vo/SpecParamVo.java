package com.weimok.common.api.goods.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-08-15:42
 */
@Data
public class SpecParamVo implements Serializable {
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
