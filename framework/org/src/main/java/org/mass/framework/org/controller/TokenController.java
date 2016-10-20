package org.mass.framework.org.controller;

import org.mass.framework.common.utils.HtmlUtil;
import org.mass.framework.common.utils.URLUtils;
import org.mass.framework.core.controller.BaseController;
import org.mass.framework.org.annotation.Auth;
import org.mass.framework.org.bean.Token;
import org.mass.framework.org.model.TokenModel;
import org.mass.framework.org.service.TokenService;
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
@RequestMapping("/token")
@Auth(verifyLogin = true,verifyURL = true)
public class TokenController extends BaseController {

	private final static Logger log= Logger.getLogger(TokenController.class);

	@Autowired(required=false)
	private TokenService tokenService;

	/**
	 *
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView  list(TokenModel model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = URLUtils.getRootMap();
		return forward("token/list",context);
	}


	/**
	 * ilook 首页
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataList")
	public void  datalist(TokenModel model,HttpServletResponse response) throws Exception{
		List<Token> dataList = tokenService.queryByList(model);
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
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
	public void save(Token bean,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		if(bean.getId() == null){
			tokenService.add(bean);
		}else{
			tokenService.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}

	@RequestMapping("/getId")
    public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap();
		Token bean  = tokenService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}


	@RequestMapping("/delete")
	public void delete(Integer id,HttpServletResponse response) throws Exception{
		tokenService.delete(id);
		sendSuccessMessage(response, "删除成功");
	}

}
