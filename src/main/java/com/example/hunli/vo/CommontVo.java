package com.example.hunli.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommontVo {
    /**
     * 客人姓名
     */
    private String name;
    /**
     * 人数
     */
    private int person;
    /**
     * 1。很高兴参加
     * 2。很抱歉无法参加
     */
    private int presence;
    /**
     * 评论
     */
    private String comment;

    private Date created_at;

    private Date updated_at;
}
