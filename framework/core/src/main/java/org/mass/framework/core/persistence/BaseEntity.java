package org.mass.framework.core.persistence;

import java.util.Date;

/**
 * Created by allen on 2015-11-17.
 */
public abstract class BaseEntity extends IdEntity {

    private String name;

    private Date createDate;

    private Date modifiedDate;

    private Integer createBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

}
