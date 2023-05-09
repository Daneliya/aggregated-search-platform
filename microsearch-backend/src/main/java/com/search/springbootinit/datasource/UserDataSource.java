package com.search.springbootinit.datasource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.search.springbootinit.common.ErrorCode;
import com.search.springbootinit.constant.CommonConstant;
import com.search.springbootinit.exception.BusinessException;
import com.search.springbootinit.mapper.UserMapper;
import com.search.springbootinit.model.dto.user.UserQueryRequest;
import com.search.springbootinit.model.entity.User;
import com.search.springbootinit.model.enums.UserRoleEnum;
import com.search.springbootinit.model.vo.LoginUserVO;
import com.search.springbootinit.model.vo.UserVO;
import com.search.springbootinit.service.UserService;
import com.search.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.search.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现
 *
 * @author frank123.xu
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserVoByPage(userQueryRequest);
    }
}
