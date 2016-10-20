package org.mass.framework.org.service;

import org.mass.framework.core.criteria.Query;
import org.apache.log4j.Logger;
import org.mass.framework.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mass.framework.org.repository.AppUserRepository;
import org.mass.framework.org.bean.AppUser;

@Service("appUserService")
public class AppUserService extends BaseService<AppUser> {

private final static Logger log= Logger.getLogger(AppUserService.class);

	@Autowired
    private AppUserRepository mapper;

	public AppUserRepository getMapper() {
		return mapper;
	}

	public AppUser getAppUser(String login,String password) {
		return queryByOne(Query.where("login",login).and("password",password));
	}

}
