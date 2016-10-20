package org.mass.framework.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mass.framework.core.service.BaseService;
import org.mass.framework.org.bean.SysMenu;
import org.mass.framework.org.bean.SysMenuBtn;
import org.mass.framework.org.bean.SysRoleRel;
import org.mass.framework.org.repository.SysMenuRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysMenuService")
public class SysMenuService extends BaseService<SysMenu> {
	private final static Logger log= Logger.getLogger(SysMenuService.class);


	@Autowired
	private SysRoleRelService sysRoleRelService;
	
	@Autowired
	private SysMenuBtnService sysMenuBtnService;
	
	@Autowired
    private SysMenuRepository mapper;
	
	/**
	 * 保存菜单btn
	 * @param btns
	 * @throws Exception 
	 */
	public void saveBtns(Integer menuid,List<SysMenuBtn> btns) throws Exception{
		if(btns == null || btns.isEmpty()){
			return;
		}
		for (SysMenuBtn btn : btns) {
			if(btn.getId()!= null && "1".equals(btn.getDeleteFlag())){
				sysMenuBtnService.delete(btn.getId());
				continue;
			}
			btn.setMenuid(menuid);
			if(btn.getId() == null){
				sysMenuBtnService.add(btn);
			}else{
				sysMenuBtnService.update(btn);
			}
		}
		
	}

	public void addMenu(SysMenu menu) throws Exception {
		super.add(menu);
		saveBtns(menu.getId(),menu.getBtns());
	}


	public void updateMenu(SysMenu menu) throws Exception {
		super.update(menu);
		saveBtns(menu.getId(),menu.getBtns());
	}


	/**
	 * 查询所有系统菜单列表
	 * @return
	 */
	public List<SysMenu> queryByAll(){
		return mapper.queryByAll();
	}
	
	/**
	 * 获取顶级菜单
	 * @return
	 */
	public List<SysMenu> getRootMenu(Integer menuId){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("menuId", menuId);
		return mapper.getRootMenu(map);
	}
	
	/**
	 * 获取子菜单
	 * @return
	 */
	public List<SysMenu> getChildMenu(){
		return mapper.getChildMenu();
	}
	
	/**
	 * 根据用户id查询父菜单
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getRootMenuByUser(Integer userId){
		return getMapper().getRootMenuByUser(userId);
	}
	
	
	/**
	 * 根据用户id查询子菜单
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getChildMenuByUser(Integer userId){
		return getMapper().getChildMenuByUser(userId);
	}
	
	
	/**
	 * 根据权限id查询菜单
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getMenuByRoleId(Integer roleId){
		return getMapper().getMenuByRoleId(roleId);
	}
	
	
	
	@Override
	public void delete(Object[] ids) throws Exception {
		super.delete(ids);
		//删除关联关系
		for(Object id : ids){
			sysRoleRelService.deleteByObjId((Integer)id, SysRoleRel.RelType.MENU.key);
			sysMenuBtnService.deleteByMenuid((Integer)id);
		}
	}

	
	public SysMenuRepository getMapper() {
		return mapper;
	}


}
