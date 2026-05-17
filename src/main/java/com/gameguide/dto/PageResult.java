package com.gameguide.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageResult<T> {
    private long total;
    private int page;
    private int size;
    private List<T> list;

    public static <T> PageResult<T> of(long total, int page, int size, List<T> list) {
        PageResult<T> r = new PageResult<>();
        r.total = total;
        r.page = page;
        r.size = size;
        r.list = list;
        return r;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("total", total);
        m.put("page", page);
        m.put("size", size);
        m.put("list", list);
        return m;
    }

    // getters for JSON serialization
    public long getTotal() { return total; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public List<T> getList() { return list; }
}
