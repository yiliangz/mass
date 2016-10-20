package org.mass.framework.redis.service;

import org.mass.framework.redis.annotation.CacheEvict;
import org.mass.framework.redis.annotation.Cacheable;
import org.mass.framework.redis.constant.DateUnit;
import org.mass.framework.redis.samples.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/1/9.
 */
@Service
public class CacheAopTestService {

    @Cacheable(key="#{player.userName}",expire = 200)
    public Player getBeanData(Player player, Integer intNum) {
        System.out.println("this is redis bean test...");
        player.setUserName("Green");
        return player;
    }

    @Cacheable(category = "forTest",key = "#{map[userName]}",expire = 1000)
    public Map getMapData(Map map) {
        System.out.println("this is redis map test...");
        return map;
    }

    @Cacheable(key = "#{pageNo}_#{pageSize}")
    public List getListData(Integer pageNo, Integer pageSize) {
        Player player = new Player();
        player.setUserName("zhang");
        List list = new ArrayList();
        list.add(player);
        return list;
    }

    @CacheEvict(category = "forTest",key = "#{map[userName]}")
    public Map updateMapData(Map map) {
        System.out.println("this is evict map test...");
        return map;
    }

}