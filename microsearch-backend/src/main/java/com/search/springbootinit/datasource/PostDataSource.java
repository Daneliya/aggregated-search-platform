package com.search.springbootinit.datasource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.search.springbootinit.common.ErrorCode;
import com.search.springbootinit.constant.CommonConstant;
import com.search.springbootinit.exception.BusinessException;
import com.search.springbootinit.exception.ThrowUtils;
import com.search.springbootinit.mapper.PostFavourMapper;
import com.search.springbootinit.mapper.PostMapper;
import com.search.springbootinit.mapper.PostThumbMapper;
import com.search.springbootinit.model.dto.post.PostEsDTO;
import com.search.springbootinit.model.dto.post.PostQueryRequest;
import com.search.springbootinit.model.entity.Post;
import com.search.springbootinit.model.entity.PostFavour;
import com.search.springbootinit.model.entity.PostThumb;
import com.search.springbootinit.model.entity.User;
import com.search.springbootinit.model.vo.PostVO;
import com.search.springbootinit.model.vo.UserVO;
import com.search.springbootinit.service.PostService;
import com.search.springbootinit.service.UserService;
import com.search.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子服务实现
 *
 * @author frank123.xu
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;

    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return postService.listPostVOByPage(postQueryRequest, request);
    }
}




