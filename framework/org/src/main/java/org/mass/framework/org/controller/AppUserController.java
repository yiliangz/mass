package org.mass.framework.org.controller;

import org.mass.framework.common.utils.URLUtils;
import org.mass.framework.core.controller.BaseController;
import org.mass.framework.org.annotation.Auth;
import org.mass.framework.common.utils.HtmlUtil;
import org.mass.framework.org.bean.AppUser;
import org.mass.framework.org.model.AppUserModel;
import org.mass.framework.org.service.AppUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appUser")
@Auth(verifyLogin = true,verifyURL = true)
public class AppUserController extends BaseController {

	private final static Logger log= Logger.getLogger(AppUserController.class);

	@Autowired(required=false)
	private AppUserService appUserService;

	/**
	 *
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView  list(AppUserModel model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = URLUtils.getRootMap();
		return forward("appUser/list",context);
	}


	/**
	 * ilook 首页
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataList")
	public void  datalist(AppUserModel model,HttpServletResponse response) throws Exception{
		List<AppUser> dataList = appUserService.queryByList(model);
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",model.getPager().getRowCount());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}

	/**
	 * 添加或修改数据
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(AppUser bean,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		if(bean.getUserId() == null){
			appUserService.add(bean);
		}else{
			appUserService.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}

	@RequestMapping("/getId")
    public void getId(Integer userId,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap();
		AppUser bean  = appUserService.queryById(userId);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}


	@RequestMapping("/delete")
	public void delete(Integer userId,HttpServletResponse response) throws Exception{
		appUserService.delete(userId);
		sendSuccessMessage(response, "删除成功");
	}

}
