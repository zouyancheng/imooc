package com.imooc.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".happymmall.com";
    private final static String COOKIE_NAME ="mmall_login_token";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks!=null){
            for(Cookie ck : cks){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
       Cookie[] cks = request.getCookies();
       if(cks !=null){
           for(Cookie ck : cks){
               if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                   ck.setDomain(COOKIE_DOMAIN);
                   ck.setPath("/");
                   ck.setMaxAge(0);
                   response.addCookie(ck);
                   return;
               }
           }
       }
    }

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位是秒
        //如果这里maxage不设置，cookie就不会写入硬盘，而是写在内存，在当前页面有效
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1 代表永久
        log.info("write cookieName{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }
}
