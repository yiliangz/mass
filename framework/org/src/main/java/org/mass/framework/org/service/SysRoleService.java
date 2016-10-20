package org.mass.framework.org.service;

import java.util.List;

import org.mass.framework.core.bean.BaseBean;
import org.mass.framework.core.service.BaseService;
import org.mass.framework.org.bean.SysRole;
import org.mass.framework.org.repository.SysRoleRepository;
import org.mass.framework.org.bean.SysRoleRel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysRoleService")
public class SysRoleService extends BaseService<SysRole> {
	private final static Logger log= Logger.getLogger(SysRoleService.class);
	
	@Autowired
	private SysRoleRelService sysRoleRelService;
	
	/**
	 * 添加角色&菜单关系
	 */
	public void addRoleMenuRel(Integer roleId,Integer[] menuIds) throws Exception{
		if(roleId == null ||  menuIds == null || menuIds.length < 1 ){ 
			return;
		}
		for(Integer menuid :menuIds ){ 
			SysRoleRel rel = new SysRoleRel();
			rel.setRoleId(roleId);
			rel.setObjId(menuid);
			rel.setRelType(SysRoleRel.RelType.MENU.key);
			sysRoleRelService.add(rel);
		}
	}
		
	/**
	 * 添加角色&菜单关系
	 */
	public void addRoleBtnRel(Integer roleId,Integer[] btnIds) throws Exception{
		if(roleId == null ||  btnIds == null || btnIds.length < 1 ){ 
			return;
		}
		for(Integer btnid : btnIds ){ 
			SysRoleRel rel = new SysRoleRel();
			rel.setRoleId(roleId);
			rel.setObjId(btnid);
			rel.setRelType(SysRoleRel.RelType.BTN.key);
			sysRoleRelService.add(rel);
		}
	}
		
	
	/**
	 * 添加
	 * @param role
	 * @param menuIds
	 * @throws Exception
	 */
	public void add(SysRole role,Integer[] menuIds,Integer[] btnIds) throws Exception {
		super.add(role);
		addRoleMenuRel(role.getId(),menuIds);
		addRoleBtnRel(role.getId(),btnIds);
	}

	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer[] ids) throws Exception {
		super.delete(ids);
		for(Integer id : ids){
			//清除关联关系
			sysRoleRelService.deleteByRoleId(id);
		}
	}

	/**
	 * 修改
	 * @param role
	 * @param menuIds
	 * @throws Exception
	 */
	public void update(SysRole role,Integer[] menuIds,Integer[] btnIds) throws Exception {
		super.update(role);
		//如果角色被禁用则删除关联的用户关系
		if(BaseBean.STATE.DISABLE.key == role.getState()){
			sysRoleRelService.deleteByRoleId(role.getId(), SysRoleRel.RelType.USER.key);
		}
		//清除关联关系
		sysRoleRelService.deleteByRoleId(role.getId(), SysRoleRel.RelType.MENU.key);
		sysRoleRelService.deleteByRoleId(role.getId(), SysRoleRel.RelType.BTN.key);
			addRoleMenuRel(role.getId(),menuIds);
			addRoleBtnRel(role.getId(),btnIds);
		
	}

	
	/**
	 *查询全部有效的权限
	 * @return
	 */
	public List<SysRole> queryAllList(){
		return getMapper().queryAllList();
	}

	

	/**
	 *查询全部有效的权限
	 * @return
	 */
	public List<SysRole> queryByUserid(Integer userid){
		return getMapper().queryByUserid(userid);
	}

	@Autowired
    private SysRoleRepository mapper;

		
	public SysRoleRepository getMapper() {
		return mapper;
	}

}
