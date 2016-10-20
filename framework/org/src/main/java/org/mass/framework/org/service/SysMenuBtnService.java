package org.mass.framework.org.service;

import org.apache.log4j.Logger;
import org.mass.framework.core.service.BaseService;
import org.mass.framework.org.bean.SysMenuBtn;
import org.mass.framework.org.repository.SysMenuBtnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("sysMenuBtnService")
public class SysMenuBtnService extends BaseService<SysMenuBtn> {
	private final static Logger log= Logger.getLogger(SysMenuBtnService.class);
	
	public List<SysMenuBtn> queryByAll(){
		return getMapper().queryByAll();
	}
	
	public List<SysMenuBtn> queryByMenuid(Integer menuid){
		return getMapper().queryByMenuid(menuid);
	}
	
	public List<SysMenuBtn> queryByMenuUrl(String url){
		return getMapper().queryByMenuUrl(url);
	}
	
	public void deleteByMenuid(Integer menuid){
		getMapper().deleteByMenuid(menuid);
	}
	
	public List<SysMenuBtn> getMenuBtnByUser(Integer userid){
		return getMapper().getMenuBtnByUser(userid);
	}

	@Autowired
    private SysMenuBtnRepository mapper;

		
	public SysMenuBtnRepository getMapper() {
		return mapper;
	}

}
