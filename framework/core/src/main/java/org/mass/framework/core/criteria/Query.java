package org.mass.framework.core.criteria;

import org.mass.framework.core.utils.Pager;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by Allen on 2015/12/29.
 */

//Query.where("name_eq","chen").and("age_gt",2).page(1,10).desc("name");

public class Query extends HashMap<String,Object> {

    public Query() {
        this.put("pager",pager);
        this.put("page",page);
        this.put("rows",rows);
    }

    private Integer page = 1;

    private Integer rows =10;

    private String sort;

    private String order;

    private Integer recordCount;

    private Integer pageCount;


    /**
     * 分页导航
     */
    private Pager pager = new Pager();

    public Pager getPager() {
        pager.setPageId(getPage());
        pager.setPageSize(getRows());
        String orderField="";
        if(StringUtils.isNotBlank(sort)){
            orderField = sort;
        }
        if(StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)){
            orderField +=" "+ order;
        }
        pager.setOrderField(orderField);
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
        this.page = pager.getPageId();
        this.rows = pager.getPageSize();
        this.pageCount = pager.getPageCount();
        this.recordCount = pager.getRowCount();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.put("page",page);
        this.page = page;
        this.pager.setPageId(page);
    }

    public void doPage() {
        this.put("page",page);
        this.put("rows",rows);
        pager.doPage();
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.put("rows",rows);
        this.rows = rows;
        this.pager.setPageSize(rows);
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        pager.setOrderField(sort);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
        pager.setOrderDirection("DESC".equals(order) ? false : true);
    }


    public static Query where(String field,Object value) {
        Query query = new Query();
        query.put(field,value);
        return query;
    }

    public Query and(String field,Object value) {
        this.put(field, value);
        return this;
    }

    public Query page(Integer page,Integer rows) {
        this.page = page;
        this.rows = rows;
        this.put("page",page);
        this.put("rows",rows);
        pager.setPageId(page);
        pager.setPageSize(rows);
        pager.doPage();
        return this;
    }

    public Query asc(String sort) {
        return direction(sort,true);
    }

    public Query desc(String sort) {
        return direction(sort,false);
    }

    public Query direction(String sort,boolean direction) {
        pager.setOrderField(sort);
        pager.setOrderDirection(direction);
        return this;
    }

    public void setRecordCount(int rowCount) {
        this.recordCount = rowCount;
        this.getPager().setRowCount(rowCount);
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        this.pager.setPageCount(pageCount);
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setRowCount(int rowCount) {
        setRecordCount(rowCount);
    }

    public Integer getRowCount() {
        return this.recordCount;
    }

}
