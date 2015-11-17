package org.mass.framework.core.persistence;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2015/4/28.
 */
public class SearchFilter {

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE
    }

    public String field;
    public Object value;
    public Operator operator;

    public SearchFilter(String field, Operator operator, Object value) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public static List<SearchFilter> parse(Map<String,String> searchParams) {
        List<SearchFilter> filters = new ArrayList<SearchFilter>();

        for (Map.Entry<String, String> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }

            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String field = names[0];
            Operator operator = Operator.valueOf(names[1].toUpperCase());

            // 创建searchFilter
            SearchFilter filter = new SearchFilter(field, operator, value);
            filters.add(filter);
        }
        return filters;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

}
