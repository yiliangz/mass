package org.mass.framework.org.repository;

import org.mass.framework.core.repository.BaseRepository;
import org.mass.framework.org.bean.SysMenu;

import java.util.List;


public interface SysMenuRepository extends BaseRepository<SysMenu> {
	
	/**
	 * 查询所有系统菜单列表
	 * @return
	 */
	public List<SysMenu> queryByAll();
	
	
	/**
	 * 获取顶级菜单
	 * @return
	 */
	public List<SysMenu> getRootMenu(java.util.Map map);
	
	/**
	 * 获取子菜单
	 * @return
	 */
	public List<SysMenu> getChildMenu();
	
	

	/**
	 * 根据权限id查询菜单
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getMenuByRoleId(Integer roleId);
	
	
	/**
	 * 根据用户id查询父菜单菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getRootMenuByUser(Integer userId);
	
	/**
	 * 根据用户id查询子菜单菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getChildMenuByUser(Integer userId);
	
}
