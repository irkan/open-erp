package com.openerp.config;

import com.openerp.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author iahmadov
 */
@Component
public class SecurityFilter extends GenericFilterBean {
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) sr).getSession();
        
        if(session.getAttribute(Constants.USER) == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) sr1;
            httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");
            httpServletResponse.setHeader("X-Frame-Options", "DENY");
            httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
            httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
            httpServletResponse.setDateHeader("Expires", 0); // Proxies.
            ((HttpServletResponse) sr1).sendRedirect("/logout");
        } else {
            fc.doFilter(sr, sr1);
        }
    }
    
}