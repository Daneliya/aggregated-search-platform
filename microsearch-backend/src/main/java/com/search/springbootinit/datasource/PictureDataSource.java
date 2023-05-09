package com.search.springbootinit.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.search.springbootinit.common.ErrorCode;
import com.search.springbootinit.exception.BusinessException;
import com.search.springbootinit.model.entity.Picture;
import com.search.springbootinit.service.PictureService;
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片服务实现类
 *
 * @author frank.xu
 * @createDate 2023-02-08 22:01:50
 */
@Service
@SuppressWarnings("all")
public class PictureDataSource implements DataSource<Picture> {
    @Resource
    private PictureService pictureService;

    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        return pictureService.searchPicture(searchText, pageNum, pageSize);
    }
}
