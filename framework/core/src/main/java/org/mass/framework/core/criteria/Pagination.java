package org.mass.framework.core.criteria;

import org.mass.framework.core.utils.Pager;

/**
 * Created by Allen on 2016/8/16.
 */
public class Pagination<T> {

    public Pagination() {

    }

    public Pagination(Query query,T data) {
        setPager(query.getPager());
        this.data = data;
    }

    private T data;

    public  T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Pagination<T> instance(Query query,T data) {
        return new Pagination<T>(query,data);
    }

    private Integer page = 1;

    private Integer rows =10;

    private String sort;

    private String order;

    private Integer recordCount;

    private Integer pageCount;


    public void setPager(Pager pager) {
        pager.doPage();
        this.page = pager.getPageId();
        this.rows = pager.getPageSize();
        this.pageCount = pager.getPageCount();
        this.recordCount = pager.getRowCount();
        this.sort = pager.getOrderField();
        this.order = pager.isOrderDirection() ? "ASC" : "DESC";
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Pagination<T> page(Integer page,Integer rows) {
        this.page = page;
        this.rows = rows;
        return this;
    }

    public void setRecordCount(int rowCount) {
        this.recordCount = rowCount;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

}
