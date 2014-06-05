package com.lejingw.apps.myactiviti.unittest;

import static org.junit.Assert.assertEquals;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/lejingw/apps/myactiviti/unittest/activiti-spring-context.xml")
public class SpringIntegationWithAnnotationTest {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	@Rule
	public ActivitiRule activitiSpringRule;

	@Test
	@Deployment(resources = { "com/lejingw/apps/myactiviti/unittest/process/myprocess.bpmn20.xml" })
	public void simpleProcessTest() {
		runtimeService.startProcessInstanceByKey("my-process");
		assertEquals(1, runtimeService.createProcessInstanceQuery().count());

		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("Activiti is awesome!", task.getName());

		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

	@Test
	@Deployment
	public void helloTest() {
		assertEquals(runtimeService, activitiSpringRule.getRuntimeService());
		
		//read file:
		///com/lejingw/apps/myactiviti/unittest/SpringIntegationWithAnnotationTest.helloTest.bpmn20.xml
		runtimeService.startProcessInstanceByKey("helloProcess");
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}
}