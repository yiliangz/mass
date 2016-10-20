package org.mass.framework.generator.translate;

import org.mass.framework.generator.config.GenerateResource;
import org.mass.framework.generator.config.JdbcProperties;
import org.apache.maven.plugin.logging.Log;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.*;

public class CreateJava {

	private static String FS = File.separator;

	public static void create(Log log,String basedir,String pckPath,GenerateResource generateResource,JdbcProperties jdbcProperties) {
		log.info("======================");
		 CreateBean createBean=new CreateBean();
		 createBean.setMysqlInfo(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());

		 Set<String> templates = generateResource.getTemplates();

		 String mvnResourcePath =
				 basedir + File.separator + "src"
						 + File.separator + "main"
						 + File.separator + "resources" +File.separator;

		 String tableName = generateResource.getTable();
		 String codeName =  generateResource.getComment();
		 String basePackage = generateResource.getBasePackage();

		 String className= createBean.getTablesNameToClassName(tableName);
		 String lowerName =className.substring(0, 1).toLowerCase()+className.substring(1, className.length());

//		 String pckPath = rootPath + "src\\com\\binf\\";

		//java,xml文件名称
		 String modelPath = "model" + FS + className+"Model.java";
		 String beanPath =  "bean" + FS +className+".java";
		 String mapperPath = "repository" + FS + className+"Mapper.java";
		 String servicePath = "service" + FS + className+"Service.java";
		 String controllerPath = "controller" + FS + className+"Action.java";
		 String sqlMapperPath = "mybatis" + FS + className+"Mapper.xml";
		//jsp页面路径
//		 String pageEditPath = lowerName+"\\"+lowerName+"Edit.jsp";
//		 String pageListPath = lowerName+"\\"+lowerName+"List.jsp";
		 
		
		VelocityContext context = new VelocityContext();
		context.put("className", className); //
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		context.put("packageName", basePackage);

		/******************************生成bean字段*********************************/
		try{
			context.put("fields",createBean.getBeanFields(tableName)); //生成bean
			context.put("isContainsDate", createBean.getIsContainsDate(tableName));
			context.put("primaryKey", createBean.getPrimaryKey(tableName));
			context.put("primaryKeyGetter", createBean.getPrimaryKeyGetter(tableName));
			context.put("toStringMethod", createBean.getToStringMethod(tableName));
		}catch(Exception e){
			e.printStackTrace();
		}

		/*******************************生成sql语句**********************************/
		try{
			Map<String,Object> sqlMap = createBean.getAutoCreateSql(tableName);
			context.put("columnDatas",createBean.getColumnDatas(tableName)); //生成bean
			context.put("SQL",sqlMap);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}

		if (templates.contains("bean")) {
			CommonPageParser.WriterPage(log, context, "TempBean.java", pckPath, beanPath);
		}
		if (templates.contains("model"))
			CommonPageParser.WriterPage(log,context, "TempModel.java",pckPath,modelPath);

		if (templates.contains("repository"))
			CommonPageParser.WriterPage(log,context, "TempRepository.java", pckPath,mapperPath);

		if (templates.contains("mybatis"))
			CommonPageParser.WriterPage(log,context, "TempMapper.xml",pckPath,sqlMapperPath);

		if (templates.contains("service"))
			CommonPageParser.WriterPage(log,context, "TempService.java", pckPath,servicePath);

		if (templates.contains("controller"))
			CommonPageParser.WriterPage(log,context, "TempController.java",pckPath, controllerPath);


//		CommonPageParser.WriterPage(context, "TempModel.java",pckPath,modelPath);
//		CommonPageParser.WriterPage(context, "TempRepository.java", pckPath,mapperPath);
//		CommonPageParser.WriterPage(context, "TempService.java", pckPath,servicePath);
//		CommonPageParser.WriterPage(context, "TempMapper.xml",pckPath,sqlMapperPath);
//		CommonPageParser.WriterPage(context, "TempController.java",actionPath, controllerPath);

//		context.put("basePath", "${e:basePath()}");
//		CommonPageParser.WriterPage(context, "TempList.jsp",webPath, pageListPath);
//		CommonPageParser.WriterPage(context, "TempEdit.jsp",webPath, pageEditPath);

	}
	
	
	public static String getRootPath(){
		String rootPath ="";
		try{
			 File file = new File(CommonPageParser.class.getResource("/").getFile());
			 rootPath = file.getParentFile().getParentFile().getParent()+"\\";
			 rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
			 System.out.println(rootPath);
			 return rootPath;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rootPath;
	}
}
