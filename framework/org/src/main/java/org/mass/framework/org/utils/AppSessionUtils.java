package org.mass.framework.org.utils;

import org.mass.framework.org.bean.AppUser;
import org.mass.framework.org.bean.SysUser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Allen on 2015-12-28 0028.
 */
public class AppSessionUtils {
    protected static final Logger log = Logger.getLogger(AppSessionUtils.class);

    private static final String SESSION_APP_USER = "SESSION_APP_USER";


    /**
     * 设置用户信息 到session
     * @param request
     * @param appUser
     */
    public static void setUser(HttpServletRequest request,AppUser appUser){
        log.debug("----------------" );
        log.debug("设置用户信息: " + appUser);
        HttpSession session  =  request.getSession();
        session.setAttribute(SESSION_APP_USER, appUser);
    }

    /**
     * 从session中获取用户信息
     * @param request
     * @return AppUser
     */
    public static AppUser getUser(HttpServletRequest request){
        return (AppUser)request.getSession(true).getAttribute(SESSION_APP_USER);
    }

    /**
     * 从session中获取app用户信息
     * @param request
     * @return Integer
     */
    public static Integer getUserId(HttpServletRequest request){
        AppUser appUser = getUser(request);
        if(appUser != null){
            return appUser.getUserId();
        }
        return null;
    }

    /**
     * 删除Session值
     * @param request
     * @param key
     */
    public static void removeAttr(HttpServletRequest request,String key){
        request.getSession(true).removeAttribute(key);
    }

    /**
     * 删除Session
     * @param request
     */
    public static void removeUser(HttpServletRequest request){
        removeAttr(request, SESSION_APP_USER);
    }
}
