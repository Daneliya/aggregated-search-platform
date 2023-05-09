package com.search.springbootinit.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.search.springbootinit.model.entity.Picture;
import com.search.springbootinit.model.entity.Post;
import com.search.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author frank.xu
 * @createDate 2023-02-08 22:01:50
 */
@SpringBootTest
@SuppressWarnings("all")
public class CrawlerTest {
    @Resource
    private PostService postService;

    @Test
    void FetchPassageTest() {
        //获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo\n";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute().body();
//        System.out.println(result);
//        数据格式转换
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = data.getJSONArray("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject record1 = (JSONObject) record;
            Post post = new Post();
            post.setTitle(record1.getStr("title"));
            post.setContent(record1.getStr("content"));
            JSONArray tags = record1.getJSONArray("tags");
            List<String> objects = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(objects));
            post.setThumbNum(1);
            post.setFavourNum(1);
            post.setUserId(1L);
            postList.add(post);
        }
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
        System.out.println(postList);
    }

    @Test
    void FetchPictureTest() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=%E9%A3%8E%E6%99%AF&qs=n&form=QBILPG&sp=-1&lq=0&pq=feng%27j&sc=10-6&cvid=7456A79E64FA485381ECE75CECFB2C0E&ghsh=0&ghacc=0&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements select = doc.select(".iuscp.isv");
        List<Picture> list = new ArrayList<>();
        for (Element element : select) {
            //取图片地址
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            //创建图片对象
            Picture picture = new Picture();
            picture.setUrl(murl);
            picture.setTitle(title);
            list.add(picture);
        }
        System.out.println(list);
    }
}
