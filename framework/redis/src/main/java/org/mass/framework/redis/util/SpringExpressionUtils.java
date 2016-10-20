package org.mass.framework.redis.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * Created by Allen on 2016/8/11.
 */
public class SpringExpressionUtils {

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     * @return
     */
    public static String parseKey(String key, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        ParserContext parserContext = new TemplateParserContext();

        //把 #{ 替换成 #{# ,以适配SpEl模板的格式
        Object returnVal =
                parser.parseExpression(key.replace("#{","#{#"), parserContext).getValue(context, Object.class);
        return returnVal == null ? null: returnVal.toString();
    }

}
