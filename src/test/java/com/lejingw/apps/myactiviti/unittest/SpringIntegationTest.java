package com.lejingw.apps.myactiviti.unittest;

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIntegationTest {

	private ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"com/lejingw/apps/myactiviti/unittest/activiti-spring-context.xml");

	@Test
	public void testExpressions() {
		RepositoryService repositoryService = (RepositoryService) applicationContext
				.getBean("repositoryService");
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/lejingw/apps/myactiviti/unittest/helloprocess.bpmn20.xml")
				.deploy();

		UserBean userBean = (UserBean) applicationContext.getBean("userBean");
		userBean.hello("helloProcess");
	}

	@Test
	public void testAutoDeployment() {
		UserBean userBean = (UserBean) applicationContext.getBean("userBean");
		userBean.hello("helloProcessAutoDeployed");
	}
}
