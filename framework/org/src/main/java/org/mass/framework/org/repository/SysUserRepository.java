package org.mass.framework.org.repository;

import org.mass.framework.core.repository.BaseRepository;
import org.mass.framework.org.bean.SysUser;
import org.mass.framework.org.model.SysUserModel;

public interface SysUserRepository extends BaseRepository<SysUser> {
	
	/**
	 * 检查登录
	 * @param email
	 * @param pwd
	 * @return
	 */
	public SysUser queryLogin(SysUserModel model);
	
	
	/**
	 * 查询邮箱总数，检查是否存在
	 * @param email
	 * @return
	 */
	public int getUserCountByEmail(String email);

}
