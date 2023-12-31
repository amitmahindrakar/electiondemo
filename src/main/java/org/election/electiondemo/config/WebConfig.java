package org.election.electiondemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index.html");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //TODO THIS IS BAD. Only added this for the sake of demo.
    registry.addMapping("/**");
  }
}
