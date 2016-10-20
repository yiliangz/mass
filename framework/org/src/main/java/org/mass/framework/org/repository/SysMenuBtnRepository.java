package org.mass.framework.org.repository;

import org.mass.framework.core.repository.BaseRepository;
import org.mass.framework.org.bean.SysMenuBtn;

import java.util.List;

public interface SysMenuBtnRepository extends BaseRepository<SysMenuBtn> {
	
	public List<SysMenuBtn> queryByMenuid(Integer menuid);
	
	public List<SysMenuBtn> queryByMenuUrl(String url);
	
	public void deleteByMenuid(Integer menuid);
	
	public List<SysMenuBtn> getMenuBtnByUser(Integer userid);
	
	public List<SysMenuBtn> queryByAll();
}
