package com.search.springbootinit.job.once;

import com.search.springbootinit.esdao.PostEsDao;
import com.search.springbootinit.service.PostService;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 全量同步帖子到 es
 *
 * @author frank123.xu
 */
// todo 取消注释开启任务
//@Component
@Slf4j
@SuppressWarnings("all")
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {

    }
}
