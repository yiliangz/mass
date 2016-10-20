package org.mass.framework.org.service;

import org.apache.log4j.Logger;
import org.mass.framework.core.criteria.Query;
import org.mass.framework.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mass.framework.org.repository.TokenRepository;
import org.mass.framework.org.bean.Token;

@Service("tokenService")
public class TokenService extends BaseService<Token> {

private final static Logger log= Logger.getLogger(TokenService.class);

	@Autowired
    private TokenRepository mapper;

	public TokenRepository getMapper() {
		return mapper;
	}

	public Token getTokenByCode(String code) {
		return queryByOne(Query.where("code", code));
	}

}
