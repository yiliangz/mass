package org.mass.framework.org.token;

import org.mass.framework.common.utils.DateUtil;
import org.mass.framework.common.utils.EncryUtil;
import org.mass.framework.common.utils.SystemConfig;
import org.mass.framework.org.bean.Token;
import org.mass.framework.org.constant.TokenConstant;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by Allen on 2015-12-28.
 */
public abstract class AbstractTokenFactory implements TokenFactory {

    public String generateTokenCode(String[] params) {
        StringBuffer buffer = new StringBuffer();
        for (String param : params) {
            buffer.append(param);
        }
        buffer.append("-_-mass-_-salt-_-=-" + System.currentTimeMillis());
        return EncryUtil.getMD5(buffer.toString());
    }

    public Token generateLoginToken(String login,String loginPassword) {
        String tokenCode = generateTokenCode(new String[]{login,loginPassword});
        return generateSimpleToken(tokenCode);
    }

    public Token generateSimpleToken(String tokenCode) {
        String durationStr = SystemConfig.getProperty("app.token.duration");
        //--------------------------------------------------------------------
        //生成不重复的随机码
        //--------------------------------------------------------------------
        int duration = 3600 * 24 * 90;
        if (!StringUtils.isEmpty(durationStr)) {
            duration = new Integer(durationStr) * 3600 * 24;
        }
        Date expiredTime = DateUtil.addSecond(new Date(),duration);
        Token token = new Token(tokenCode,duration,expiredTime);
        return token;
    }

}
