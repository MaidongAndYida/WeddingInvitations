package com.example.hunli.vo;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private int current_page;

    private List<T> data;

    private String first_page_url;

    private int from;

    private int last_page;

    private String last_page_url;

    private String next_page_url;

    private String path;

    private int per_page;

    private String prev_page_url;

    private int to;

    private int total;
}
