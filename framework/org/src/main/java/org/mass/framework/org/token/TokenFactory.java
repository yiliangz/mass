package org.mass.framework.org.token;

import org.mass.framework.org.bean.Token;
import org.mass.framework.org.service.TokenService;

/**
 * Created by Allen on 2015-12-25.
 */
public interface TokenFactory {

    public boolean validToken(Token token) throws Exception;

    public String generateTokenCode(String[] params);

    public Token generateToken(String login,String loginPassword) throws Exception;

    public Token generateLoginToken(String login,String loginPassword);

    public Token generateSimpleToken(String tokenCode);

    public Token saveToken(Token token) throws Exception;

}
