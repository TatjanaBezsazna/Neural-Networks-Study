package com.neuralnetwork;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;


@EnableAutoConfiguration
@Import(AppContext.class)
@ComponentScan("com.neuralnetwork.ui")
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Configuration
	@ConditionalOnClass({ Servlet.class })
	public static class DefaultTemplateResolverConfiguration {

		@Autowired
		private final ResourceLoader resourceLoader = new DefaultResourceLoader();

		@Bean
		public VelocityConfig velocityConfig() {
			VelocityConfigurer cfg = new VelocityConfigurer();
			cfg.setResourceLoader(resourceLoader);
			return cfg;
		}

		@Bean
		public ViewResolver viewResolver() {
			VelocityViewResolver resolver = new VelocityViewResolver();
			resolver.setViewClass(VelocityToolboxView.class);
			resolver.setPrefix("/templates/");
			resolver.setSuffix(".vm");
			resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 20);
			return resolver;
		}
	}

}
