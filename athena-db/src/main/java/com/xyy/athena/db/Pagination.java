package com.xyy.athena.db;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Pagination
 *
 * @author: 熊亚运
 * @date: 2019-05-22
 */
@Data
public class Pagination<T> implements Serializable {
    public final static int PAGE_INDEX = 1;
    public final static int PAGE_SIZE = 10;

    /**
     * 当前页码
     */
    private int pageIndex;
    /**
     * 每页记录条数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private long pageCount;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 分页数据
     */
    private List<T> rows;

    public Pagination(Page<T> page) {
        PageInfo<T> pageInfo = new PageInfo<>(page);
        this.pageIndex = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.pageCount = total / pageSize + ((total % pageSize) > 0 ? 1 : 0);
        this.rows = page.getResult();
    }
}
