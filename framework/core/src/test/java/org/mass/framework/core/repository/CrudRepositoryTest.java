package org.mass.framework.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mass.framework.core.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Allen on 2015-11-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CrudRepositoryTest {

    @Autowired
    public PlayerRepository playerRepository;

    @Test
    public void testGet() {
        Player player = playerRepository.get(1L);
        System.out.println();
    }

    @Test
    public void testAdd() {
        Player player = new Player();
        player.setName("dream");
        playerRepository.add(player);
    }

}
