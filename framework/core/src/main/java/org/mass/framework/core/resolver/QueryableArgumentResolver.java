package org.mass.framework.core.resolver;

import org.mass.framework.core.criteria.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * Created by Allen on 2015/12/30.
 */
public class QueryableArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        return Query.class.equals(parameter.getParameterType());
    }

    public Query resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String,String[]> map =  webRequest.getParameterMap();
        Query query = new Query();

        Integer page = parseInt(map.get("page"));
        Integer rows = parseInt(map.get("rows"));
        if (page != null) {
            query.setPage(page);
        }
        if (rows != null) {
            query.setRows(rows);
        }
        return query;
    }

    public Integer parseInt(String[] array) {
        if (array != null) {
            String result = array[0];
            if (!StringUtils.isEmpty(result)) {
                return new Integer(result);
            }
        }
        return null;
    }

    public String parseStr(String[] array) {
        if (array != null) {
            return array[0];
        }
        return null;
    }
}
