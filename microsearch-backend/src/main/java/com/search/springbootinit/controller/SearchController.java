package com.search.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.search.springbootinit.common.BaseResponse;
import com.search.springbootinit.common.ErrorCode;
import com.search.springbootinit.common.ResultUtils;
import com.search.springbootinit.exception.BusinessException;
import com.search.springbootinit.exception.ThrowUtils;
import com.search.springbootinit.manager.SearchFacde;
import com.search.springbootinit.model.dto.post.PostQueryRequest;
import com.search.springbootinit.model.dto.search.SearchRequest;
import com.search.springbootinit.model.dto.user.UserQueryRequest;
import com.search.springbootinit.model.entity.Picture;
import com.search.springbootinit.model.enums.SearchTypeEnum;
import com.search.springbootinit.model.vo.PostVO;
import com.search.springbootinit.model.vo.SearchVO;
import com.search.springbootinit.model.vo.UserVO;
import com.search.springbootinit.service.PictureService;
import com.search.springbootinit.service.PostService;
import com.search.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 聚合搜索
 *
 * @author frank.xu
 * @createDate 2023-02-08 22:01:50
 */
@RestController
@RequestMapping("/search")
@Slf4j
@SuppressWarnings("all")
public class SearchController {
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;
    @Resource
    private SearchFacde searchFacde;

    /**
     * 聚合搜索接口(门面模式)
     *
     * @param searchRequest
     * @param request
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAllByType(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        SearchVO searchVO = searchFacde.searchAllByType(searchRequest, request);
        return ResultUtils.success(searchVO);
    }

    /**
     * 聚合搜索接口 （并发）
     *
     * @param searchRequest
     * @param request
     * @return
     */
    //@PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        CompletableFuture<Page<UserVO>> completableFutureUser = CompletableFuture.supplyAsync(() -> {
            //user
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            return userService.listUserVoByPage(userQueryRequest);
        });
        CompletableFuture<Page<PostVO>> completableFuturePost = CompletableFuture.supplyAsync(() -> {
            //post
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
            return postVOPage;
        });
        CompletableFuture<Page<Picture>> completableFuturePicture = CompletableFuture.supplyAsync(() -> {
            //picture
            return pictureService.searchPicture(searchText, 1, 10);
        });
        CompletableFuture.allOf(completableFuturePicture, completableFutureUser, completableFuturePicture).join();
        try {
            Page<UserVO> userVOPage = completableFutureUser.get();
            Page<PostVO> postVOPage = completableFuturePost.get();
            Page<Picture> picturePage = completableFuturePicture.get();
            //设置返回值
            SearchVO searchVO = new SearchVO();
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("查询异常", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询错误");
        }
    }

}
