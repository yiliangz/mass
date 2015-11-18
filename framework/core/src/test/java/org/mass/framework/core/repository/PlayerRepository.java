package org.mass.framework.core.repository;

import org.mass.framework.core.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Allen on 2015-11-17 0017.
 */

public interface PlayerRepository extends CrudRepository<Player,Long> {

}
