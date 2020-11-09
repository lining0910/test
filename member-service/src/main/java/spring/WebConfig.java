/**
 * Project:taole-jc-service
 * File:WebConfig.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.taole.framework.bean.DynamicBeanFactory;
import com.taole.framework.cluster.Global;
import com.taole.framework.cluster.Node;
import com.taole.framework.cluster.Standalone;
import com.taole.framework.manager.BeanFieldWirer;
import com.taole.framework.manager.DomainEngineFactory;
import com.taole.framework.module.ModuleConfig;
import com.taole.framework.module.ModuleRegistry;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.EnvironmentHelper;
import com.taole.framework.util.MessageUtils;

@Configuration
@PropertySource(name = "web.env", value = "classpath:/env.properties")
@Import( {
	/*** import framework configs ***/
	com.taole.framework.protocol.BeanConfig.class, 
	com.taole.framework.cache.CachRegistryConfig.class,
	com.taole.framework.rest.BeanConfig.class,
	/*** import project configs ***/
	com.taole.toolkit.ProjectConfig.class,
	com.taole.member.ProjectConfig.class,
	com.taole.log.ProjectConfig.class})
public class WebConfig {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	Environment environment;

	@Bean
	WebApplication webApplication() {
		return new WebApplication();
	}
	
	@Bean
	ModuleRegistry moduleRegistry() {
		return new ModuleRegistry();
	}
	
	@Bean
	DomainEngineFactory domainEngineFactory() {
		return new DomainEngineFactory();
	}
	
	@Bean
	DynamicBeanFactory dynamicBeanFactory() {
		return new DynamicBeanFactory();
	}
	
	@Bean
	BeanFieldWirer beanFieldWirer() {
		return new BeanFieldWirer();
	}
	
	@Bean(name = "hibernate.packagesToScan")
	List<String> hibernateScanPackages() {
		List<String> packageNames = new ArrayList<String>();
		Map<String, Object> moduleConfigBeans = applicationContext.getBeansWithAnnotation(ModuleConfig.class);
		for (Map.Entry<String, Object> entry : moduleConfigBeans.entrySet()) {
			ModuleConfig mc = BeanUtils.getClass(entry.getValue()).getAnnotation(ModuleConfig.class);
			if (mc.domainPackages() != null && mc.domainPackages().length > 0) {
				packageNames.addAll(Arrays.asList(mc.domainPackages()));
			}
		}
		packageNames.add("com.taole.framework.hibernate");
		return packageNames;
	}
	
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		EnvironmentHelper helper = new EnvironmentHelper(environment);
		MutablePropertySources sources = helper.getPropertySources();
		List<String> messages = new LinkedList<String>();
		for (Iterator<org.springframework.core.env.PropertySource<?>> iterator = sources.iterator(); iterator.hasNext();) {
			org.springframework.core.env.PropertySource<?> source = iterator.next();
			Object original = source.getSource();
			if (Properties.class.isInstance(original)) {
				Properties props = Properties.class.cast(original);
				for (String key : props.stringPropertyNames()) {
					if (key.startsWith("message.i18n.")) {
						messages.add(props.getProperty(key));
					}
				}
			}
		}
		String[] messageArray = messages.toArray(new String[messages.size()]);
		CollectionUtils.reverseArray(messageArray);
		ms.setBasenames(messageArray);
		MessageUtils.setDefaultMessageSource(ms);
		return ms;
	}

	@Bean(name = "taskExecutor")
	TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(10);
		te.setMaxPoolSize(100);
		return te;
	}
	
	@Bean
	Global global() {
		return new Standalone();
	}

	@Bean
	Node node() {
		return new Node();
	}
	
}