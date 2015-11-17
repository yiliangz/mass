package org.mass.framework.core.page;

import java.util.List;

/**
 * Created by Allen on 2015/4/27.
 */
public class PageImpl<T> extends AbstractPage<T> {

    public PageImpl(Page page) {
        this.content    =   page.getContent();
        this.total      =   page.getTotal();
        this.page       =   page.getPage();
        this.size       =   page.getSize();
        this.total      =   page.getTotal();
        this.totalPage  =   page.getTotalPage();
    }

}
