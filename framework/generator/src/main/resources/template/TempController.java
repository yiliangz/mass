package ${packageName}.action;

import org.mass.framework.common.utils.URLUtils;
import org.mass.framework.core.action.BaseAction;
import org.mass.framework.org.annotation.Auth;
import org.mass.framework.common.utils.HtmlUtil;
import ${packageName}.bean.${className};
import ${packageName}.model.${className}Model;
import ${packageName}.service.${className}Service;
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
@RequestMapping("/${lowerName}")
@Auth(verifyLogin = true,verifyURL = true)
public class ${className}Controller extends BaseController {

	private final static Logger log= Logger.getLogger(${className}Action.class);

	@Autowired(required=false)
	private ${className}Service ${lowerName}Service;

	/**
	 *
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView  list(${className}Model model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = URLUtils.getRootMap();
		return forward("${lowerName}/list",context);
	}


	/**
	 * ilook 首页
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataList")
	public void  datalist(${className}Model model,HttpServletResponse response) throws Exception{
		List<${className}> dataList = ${lowerName}Service.queryByList(model);
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
	public void save(${className} bean,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		if(bean.${primaryKeyGetter}() == null){
			${lowerName}Service.add(bean);
		}else{
			${lowerName}Service.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}

	@RequestMapping("/getId")
    public void getId(Integer ${primaryKey},HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap();
		${className} bean  = ${lowerName}Service.queryById(${primaryKey});
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}


	@RequestMapping("/delete")
	public void delete(Integer ${primaryKey},HttpServletResponse response) throws Exception{
		${lowerName}Service.delete(${primaryKey});
		sendSuccessMessage(response, "删除成功");
	}

}
