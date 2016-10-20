package org.mass.framework.org.repository;

import org.mass.framework.core.repository.BaseRepository;
import org.mass.framework.org.bean.SysRole;

import java.util.List;

public interface SysRoleRepository extends BaseRepository<SysRole> {
	
	/**
	 *查询全部有效的权限
	 * @return
	 */
	public List<SysRole> queryAllList();
	
	
	/**
	 *根据用户Id查询权限
	 * @return
	 */
	public List<SysRole> queryByUserid(Integer userid);
}
