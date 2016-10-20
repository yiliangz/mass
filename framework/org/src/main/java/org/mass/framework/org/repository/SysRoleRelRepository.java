package org.mass.framework.org.repository;

import java.util.List;

import org.mass.framework.core.repository.BaseRepository;
import org.mass.framework.org.bean.SysRoleRel;

public interface SysRoleRelRepository extends BaseRepository<SysRoleRel> {
	
	public void deleteByRoleId(java.util.Map<String, Object> param);
	
	public void deleteByObjId(java.util.Map<String, Object> param);
	
	
	public List<SysRoleRel> queryByRoleId(java.util.Map<String, Object> param);

	public List<SysRoleRel> queryByObjId(java.util.Map<String, Object> param);
	
}
