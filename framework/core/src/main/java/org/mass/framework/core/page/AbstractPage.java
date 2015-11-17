package org.mass.framework.core.page;

import java.util.List;

/**
 * Created by Allen on 2015/4/27.
 */
public abstract class AbstractPage<T> implements Page<T> {

    protected static final long DEFAULT_SIZE = 10;

    protected List<T> content;

    protected long page;

    protected long size;

    protected long total;

    protected long totalPage;

    public AbstractPage() {
        this.size = this.size == 0 ? DEFAULT_SIZE : this.size;
    }

    public Page nextPage() {
        return null;
    }

    public Page prevPage() {
        return null;
    }

    public List<T> getContent() {
        return content;
    }

    public boolean isFirst() {
        return page == 0;
    }

    public boolean hasNext() {
        return total == 0 ? false : page < totalPage;
    }

    public void calculatePage(long total) {
        this.total = total;
        this.totalPage = (long) Math.ceil((double) total / (double) size);
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

}
