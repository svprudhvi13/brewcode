package in.brewcode.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.web.bind.support.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "in.brewcode.api.web",
		"in.brewcode.api.web.error", "in.brewcode.api.persistence.dao",
		"in.brewcode.api.persistence.dao.common",
		"in.brewcode.api.persistence.dao.common", "in.brewcode.api.service",
		"in.brewcode.api.service.impl", "in.brewcode.api.service.common",
		"in.brewcode.api.config", "in.brewcode.api.auth.resource.config",
		"in.brewcode.api.auth.server.config",
		"in.brewcode.api.auth.server.dao",
		"in.brewcode.api.auth.server.service",
		"in.brewcode.api.auth.server.service.impl" })
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Configured to support multipart streams
	 */
	@Override
	public void extendMessageConverters(
			final List<HttpMessageConverter<?>> converters) {
		converters.add(byteArrayHttpMessageConverter());

	}

	/**
	 * Configured to user @AuthenticationPrincipal annotation
	 */
	@Override
	public void addArgumentResolvers(
			java.util.List<org.springframework.web.method.support.HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(getAuthenticationPrincipalArgumentResolver());
	}

	/**
	 * In order to uploading multipart files
	 * 
	 * @return
	 */
	@Bean
	@Qualifier("multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000000);
		return multipartResolver;
	}

	/**
	 * Without spring boot, you have to add request handler by overriding the
	 * method in {@link WebMvcConfigurerAdapter}
	 * 
	 * @return
	 */

	@Override
	public void addResourceHandlers(
			org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations(
				"classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
		arrayHttpMessageConverter
				.setSupportedMediaTypes(getSupportedMediaTypes());

		return arrayHttpMessageConverter;
	}

	@Bean
	public AuthenticationPrincipalArgumentResolver getAuthenticationPrincipalArgumentResolver() {
		return new AuthenticationPrincipalArgumentResolver();
	}

	private List<MediaType> getSupportedMediaTypes() {
		final List<MediaType> list = new ArrayList<MediaType>();
		// list.add(MediaType.IMAGE_JPEG);
		// list.add(MediaType.IMAGE_PNG);
		list.add(MediaType.ALL);
		return list;
	}
}
