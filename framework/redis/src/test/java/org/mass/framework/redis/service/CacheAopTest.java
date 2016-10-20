package org.mass.framework.redis.service;

import org.mass.framework.redis.samples.Player;
import org.mass.framework.redis.samples.Inventor;
import org.mass.framework.redis.util.RedisUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Allen on 2016/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-common.xml"})
public class CacheAopTest {

    @Resource
    private JedisPool jedisPool;

    @Resource
    private CacheAopTestService redisTestService;

    @Test
    public void testConnection() {
        Jedis jedis =jedisPool.getResource();
        jedis.set("mobile", "18802091532");
        System.out.println(jedis.get("mobile"));
        Set list = jedis.keys("key*");
        System.out.println();
    }

    @Test
    public void testGetBeanData() {
        Player player = new Player();
        player.setUserName("curry");
        player.setAge(27);
        redisTestService.getBeanData(player, 1);
        System.out.println(player);
    }

    @Test
    public void testMapData() {
        Map map = new HashMap();
        map.put("userName","testMapName");
        redisTestService.getMapData(map);
        System.out.println(map);
    }

    @Test
    public void testListData() {
        List list = redisTestService.getListData(5, 6);
        System.out.println(list);
    }

    @Test
    public void testUpdateData() {
        Map map = new HashMap();
        map.put("userName","testMapName");
        redisTestService.updateMapData(map);
        System.out.println(map);
    }

    @Test
    public void testSpEL1() {

        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);   collection.add(5);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);    map.put("b", 2);

        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context1 = new StandardEvaluationContext();

        context1.setVariable("collection", collection);
        Collection<Integer> result1 =
                parser.parseExpression("#collection.![#this+1]").getValue(context1, Collection.class);
        Assert.assertEquals(2, result1.size());
        Assert.assertEquals(new Integer(5), result1.iterator().next());

    }

    @Test
    public void testSpEL2() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        //  The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("name");
        EvaluationContext context = new StandardEvaluationContext(tesla);

        String name = (String) exp.getValue(context);
        System.out.println(name);
    }

    @Test
    public void testClass() {

        String expression = "__#{#item},#{#address[city]}___#{#player.userName}";

        ParserContext parserContext = new TemplateParserContext();
        String prefix = parserContext.getExpressionPrefix();

        ExpressionParser parser = new SpelExpressionParser();

        StandardEvaluationContext context = getContext();

//        expression = parseExpression(expression,context);

        Expression ep = parser.parseExpression(expression, parserContext);

        Object returnVal =
                parser.parseExpression(expression, parserContext).getValue(context, Object.class);
        System.out.println(returnVal);
    }

    public StandardEvaluationContext getContext() {
        StandardEvaluationContext context = new StandardEvaluationContext();

        Player player = new Player();
        player.setUserName("Zhang");

        Map address = new HashMap();
        address.put("city","GuangZhou");

        context.setVariable("player",player);
        context.setVariable("address",address);
        context.setVariable("item","keyboard");

        return context;
    }

    @Test
    public void simpleTest() {
        String result = "__#{#item},#{#address[city]}___#{#player.userName}".replace("#{#","#{");
        System.out.println(result);
    }

    @Test
    public void testDel() {
        System.out.println("============");
        RedisUtils.del("CacheAopTestService_getMapData_testMapName");
    }

    @Test
    public void TestFlushDb() {
        System.out.println("flushDB");
        RedisUtils.flushDB();
    }


}
