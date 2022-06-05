package com.belhard.bookstore.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PageUtil {

    public static Pageable getPageRequest(Map<String, String> map) {

        int page = Integer.parseInt(map.get("page"));
        int size = Integer.parseInt(map.get("size"));
        Sort.Direction sort = Sort.Direction.valueOf(map.get("sort").toUpperCase());
        String column = map.get("column");

        return PageRequest.of(page, size, sort, column);
    }
}
