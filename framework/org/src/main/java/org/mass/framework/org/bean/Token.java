package org.mass.framework.org.bean;

import java.util.Date;

/**
 * Created by Allen on 2015-12-25.
 */
public class Token {

    private Integer id;

    //有效期
    private long duration;

    //token内容
    private String code;

    //过期时间
    private Date expiredTime;

    //创建时间
    private Date createTime;

    //token关联的id
    private int refId;

    //token关联的类型
    private String refType;

    public Token() {

    }

    public Token(String code,long duration,Date expiredTime) {
        this.code = code;
        this.duration = duration;
        this.expiredTime = expiredTime;
        this.createTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

}
