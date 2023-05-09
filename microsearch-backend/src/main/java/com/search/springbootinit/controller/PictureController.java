package com.search.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.search.springbootinit.common.BaseResponse;
import com.search.springbootinit.common.ErrorCode;
import com.search.springbootinit.common.ResultUtils;
import com.search.springbootinit.exception.ThrowUtils;
import com.search.springbootinit.model.dto.picture.PictureQueryRequest;
import com.search.springbootinit.model.entity.Picture;
import com.search.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author frank123.xu
 */
@RestController
@RequestMapping("/picture")
@Slf4j
@SuppressWarnings("all")
public class PictureController {

    @Resource
    private PictureService pictureService;


    private final static Gson GSON = new Gson();

    /**
     * 分页获取列表（封装类）
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                           HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }

}
