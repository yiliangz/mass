package org.mass.framework.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mass.framework.core.service.BaseService;
import org.mass.framework.org.repository.SysRoleRelRepository;
import org.mass.framework.org.bean.SysRoleRel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysRoleRelService")
public class SysRoleRelService extends BaseService<SysRoleRel> {
	private final static Logger log= Logger.getLogger(SysRoleRelService.class);
	
	
	public List<SysRoleRel> queryByRoleId(Integer roleId,Integer relType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		param.put("relType", relType);
		return getMapper().queryByRoleId(param);
	}
	
	
	public List<SysRoleRel> queryByObjId(Integer objId,Integer relType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("objId", objId);
		param.put("relType", relType);
		return getMapper().queryByObjId(param);
	}
	
	/**
	 * 根据关联对象id,关联类型删除 
	 * @param objId
	 * @param relType
	 */
	public void deleteByObjId(Integer objId,Integer relType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("objId", objId);
		param.put("relType", relType);
		getMapper().deleteByObjId(param);
	}
	
	/**
	 * 根据角色id删除 
	 * @param roleId
	 */
	public void deleteByRoleId(Integer roleId){
		deleteByRoleId(roleId,null);
	}
	
	/**
	 *  根据角色id,关联类型删除 
	 * @param roleId
	 * @param relType
	 */
	public void deleteByRoleId(Integer roleId,Integer relType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		param.put("relType", relType);
		getMapper().deleteByRoleId(param);
	}
	
	
	

	@Autowired
    private SysRoleRelRepository mapper;

	public SysRoleRelRepository getMapper() {
		return mapper;
	}

}
