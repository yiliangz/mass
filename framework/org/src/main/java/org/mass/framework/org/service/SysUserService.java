package org.mass.framework.org.service;

import org.mass.framework.core.service.BaseService;
import org.mass.framework.org.bean.SysUser;
import org.mass.framework.org.repository.SysUserRepository;
import org.mass.framework.org.bean.SysRoleRel;
import org.mass.framework.org.model.SysUserModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserService")
public class SysUserService extends BaseService<SysUser> {
	private final static Logger log= Logger.getLogger(SysUserService.class);
	
	@Autowired
	private SysRoleRelService sysRoleRelService;

	@Override
	public void delete(Object[] ids) throws Exception {
		super.delete(ids);
		for(Object id :  ids){
			sysRoleRelService.deleteByObjId((Integer)id, SysRoleRel.RelType.USER.key);
		}
	}
	/**
	 * 检查登录
	 * @param email
	 * @param pwd
	 * @return
	 */
	public SysUser queryLogin(String email,String pwd){
		SysUserModel model = new SysUserModel();
		model.setEmail(email);
		model.setPwd(pwd);
		return getMapper().queryLogin(model);
	}
	
	/**
	 * 查询邮箱总数，检查是否存在
	 * @param email
	 * @return
	 */
	public int getUserCountByEmail(String email){
		return getMapper().getUserCountByEmail(email);
	}
	
	/**
	 * 查询用户权限
	 * @param userId
	 * @return
	 */
	public List<SysRoleRel> getUserRole(Integer userId){
		return sysRoleRelService.queryByObjId(userId, SysRoleRel.RelType.USER.key);
	}
	
	/**
	 * 添加用户权限
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void addUserRole(Integer userId,Integer[] roleIds) throws Exception{
		if(userId == null ||  roleIds == null || roleIds.length < 1 ){ 
			return;
		}
		//清除关联关系
		sysRoleRelService.deleteByObjId(userId, SysRoleRel.RelType.USER.key);
		for(Integer roleId :roleIds ){ 
			SysRoleRel rel = new SysRoleRel();
			rel.setRoleId(roleId);
			rel.setObjId(userId);
			rel.setRelType(SysRoleRel.RelType.USER.key);
			sysRoleRelService.add(rel);
		}
	}
	
	
	@Autowired
    private SysUserRepository mapper;

		
	public SysUserRepository getMapper() {
		return mapper;
	}

}
