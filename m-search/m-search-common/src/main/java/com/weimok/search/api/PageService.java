package com.weimok.search.api;

import java.util.Map;

/**
 * @author itsNine
 * @create 2020-04-10-17:00
 */
public interface PageService {
    Map<String,Object> loadModel(Long spuId);
}
