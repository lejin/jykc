package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/api/*" })
public class ApiAccessLogger implements Filter{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiAccessLogger.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) arg0;
        HttpServletResponse httpResponse= (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replaceAll("[/]+$", "");
        logger.info("________________________________________");
        logger.info(">>>>    path >>>>> "+path);
        httpRequest.getParameterMap().forEach((k,v)-> {
            String[] blah=(String[])v;
            logger.info(">>>> "+k +"  <<  "+v[0]);
        });
        logger.info("________________________________________");
        chain.doFilter(httpRequest, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
