package com.weimok.portal.page;

import com.weimok.search.api.PageService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author itsNine
 * @create 2020-04-10-17:07
 */
@Controller
public class PageController {
    @Reference(version = "1.0.0")
    private PageService pageService;

    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model){
        //查询模型数据
        Map<String, Object> attributes = pageService.loadModel(spuId);
        //准备模型数据源
        model.addAllAttributes(attributes);

        return "item";
    }
}
