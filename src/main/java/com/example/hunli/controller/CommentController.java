package com.example.hunli.controller;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import com.example.hunli.vo.CommontVo;
import com.example.hunli.vo.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class CommentController {

    private static final String URL_PREFIX = "http:\\/\\/localhost:8080\\/comments?page=";

    @Resource    // 自动注入，spring boot会帮我们实例化一个对象
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("comments")
    public Page getCommentsList(Integer page){

        log.info("getCommentsList:{}", page);

        //总条数
        Integer totalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hunli", Integer.class);
        log.info("打印totalCount: {}", totalCount);
        if(page == null || page < 0){
            page = 0;
        }
        int totalPage = (totalCount + 3 - 1)/3;
        if(page > totalPage){
            //returnData.setNext_page_url(null);
            page = totalPage;
        }
        log.info("打印page:{}", page);
        String queryListSql = "select * from hunli order by created_at desc limit ?,?";
        List<CommontVo> list = jdbcTemplate.query(queryListSql,
                new Object[]{page*3, 3},
                new BeanPropertyRowMapper<>(CommontVo.class)
        );
        log.info("打印listsize:{}", list.size());
        if(list.isEmpty()){
            return null;
        }
        Page<CommontVo> returnData = new Page<>();

        int perPage = page-1>0?page-1:1;
        int nextPage = Math.min(page + 1, totalPage);

        returnData.setCurrent_page(page);
        returnData.setData(list);
        returnData.setFirst_page_url(URL_PREFIX + 1);
        returnData.setFrom(page-1>0?page-1:1);
        returnData.setLast_page(totalPage);
        returnData.setLast_page_url("http:\\/\\/localhost:8080\\/comments?page="+totalPage);
        returnData.setNext_page_url("http:\\/\\/localhost:8080\\/comments?page="+nextPage);
        returnData.setPath("http:\\/\\/localhost:8080\\/comments");
        returnData.setPer_page(perPage);
        returnData.setPrev_page_url("http:\\/\\/localhost:8080\\/comments?page=" + perPage);
        returnData.setTo(nextPage);
        returnData.setTotal(totalCount);

        return returnData;
    }

    @RequestMapping("comment/store")
    public void store(@RequestBody CommontVo commontVo){
        log.info("打印commontVo:{}", commontVo.toString());

        String sql = "INSERT INTO hunli (name, person, presence, comment)\n" +
                "VALUES ('"+commontVo.getName()+"', '"+commontVo.getPerson()+"', '"+commontVo.getPresence()+"', '"+commontVo.getComment()+"')";

        jdbcTemplate.execute(sql);

    }
}
