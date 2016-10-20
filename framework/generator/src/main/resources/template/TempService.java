package ${packageName}.service;

import org.apache.log4j.Logger;
import org.mass.framework.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.mapper.${className}Mapper;
import ${packageName}.bean.${className};

@Service("$!{lowerName}Service")
public class ${className}Service extends BaseService<${className}> {

private final static Logger log= Logger.getLogger(${className}Service.class);

	@Autowired
    private ${className}Mapper mapper;

	public ${className}Mapper getMapper() {
		return mapper;
	}

}
