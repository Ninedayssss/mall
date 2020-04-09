package com.weimok.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-05-10:38
 */
@Data
public class PageResult<T> implements Serializable {
    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
