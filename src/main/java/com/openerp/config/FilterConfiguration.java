package com.openerp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.DispatcherType;

@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean securityFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(getSecurityFilter());
    registration.addUrlPatterns("/admin/*",
            "/idgroup/*",
            "/accounting/*",
            "/wharehouse/*",
            "/sale/*",
            "/collect/*",
            "/hr/*",
            "/payroll/*",
            "/crm/*",
            "/profile/*",
            "/export/*",
            "/delete/*",
            "/route/*",
            "/swagger-ui/*"
    );
    registration.setOrder(1);
    return registration;
  }

  @Bean
  public SecurityFilter getSecurityFilter() {
    return new SecurityFilter();
  }

  @Bean
  public FilterRegistrationBean encodingFilter() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    bean.setFilter(new CharacterEncodingFilter());
    bean.addInitParameter("encoding", "UTF-8");
    bean.setName("encodingFilter");
    bean.addUrlPatterns("/*");
    bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
    return bean;
  }
}