package com.search.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.search.springbootinit.model.entity.Picture;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author frank.xu
 * @createDate 2023-02-08 22:01:50
 */
@SuppressWarnings("all")
public interface DataSource<T> {
    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */

    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
