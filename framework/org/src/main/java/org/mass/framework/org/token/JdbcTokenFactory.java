package org.mass.framework.org.token;

import org.mass.framework.core.criteria.Query;
import org.mass.framework.org.bean.Token;
import org.mass.framework.org.model.TokenModel;
import org.mass.framework.org.service.AppUserService;
import org.mass.framework.org.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Allen on 2015-12-25.
 */
@Service("jdbcTokenFactory")
public class JdbcTokenFactory extends AbstractTokenFactory implements TokenFactory{

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TokenService tokenService;

    public boolean validToken(Token validToken) throws Exception {
        if (validToken == null) {
            return false;
        }
        Query query = Query.where("code", validToken.getCode()).and("refType",validToken.getRefType());
                Token token = tokenService.queryByOne(query);
        if (token == null) {
            return false;
        }
        //-----------------------------
        //验证token是否过期
        //-----------------------------
        return token.getExpiredTime().after(new Date());
    }

    @Transactional
    public Token generateToken(String login, String loginPassword) throws Exception {
        Token token = generateLoginToken(login, loginPassword);
        tokenService.add(token);
        return token;
    }

    @Transactional
    public Token saveToken(Token token) throws Exception {
        tokenService.add(token);
        return token;
    }

}
