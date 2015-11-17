package org.mass.framework.core.persistence;

/**
 * Created by Allen on 2015/1/18.
 */

public abstract class IdEntity {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
