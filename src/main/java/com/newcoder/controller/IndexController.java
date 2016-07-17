package com.newcoder.controller;

import com.newcoder.model.User;
import com.newcoder.service.ToutiaoService;
import com.sun.deploy.net.cookie.CookieUnavailableException;
import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by WXY on 2016/7/16.
 */
@Controller
public class IndexController {

    @Autowired
    private ToutiaoService toutiaoService;


    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session) {

        return "hello nowcoder," + session.getAttribute("msg") + "<br>" + toutiaoService.say();
    }

    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "newcoder") String key
                          ) {
        return String.format("GID{%s},UID{%d},TYPE{%d},KEY{%s}", groupId, userId, type, key);
    }

    @RequestMapping(value = "/vm")
    public String news(Model model){
        model.addAttribute("value1","vv1");

        List<String> colors = Arrays.asList(new String[] {"RED", "GREEN", "BLUE"});

        Map<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < 4; ++i){
            map.put(String.valueOf(i), String.valueOf(i*i));
        }

        model.addAttribute("colors", colors);
        model.addAttribute("map", map);
        model.addAttribute("user", new User("Jim"));

        return "news";
    }

    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for(Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }

        sb.append("Method:" + request.getMethod());
        sb.append("<br>");
        sb.append("getPathInfo:" + request.getPathInfo());
        sb.append("<br>");
        sb.append("getQueryString:" + request.getQueryString());
        sb.append("<br>");
        sb.append("RequestURI:" + request.getRequestURI());
        sb.append("<br>");

        return sb.toString() + "哈哈";
    }
    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "newcoderid", defaultValue = "a") String newcoderid,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NewCoderID from cookie " + newcoderid;
    }

    @RequestMapping(value = {"/redirect/{code}"})
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
        /*
        RedirectView redirectView = new RedirectView("/", true);
        if(code == 301){
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;*/
        session.setAttribute("msg", "this is a session");
        return  "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error : " + e.getMessage();
    }

}
