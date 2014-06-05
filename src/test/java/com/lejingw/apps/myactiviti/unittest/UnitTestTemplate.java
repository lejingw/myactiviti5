package com.lejingw.apps.myactiviti.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

public class UnitTestTemplate {
	private final String CONFIG_PATH = "com/lejingw/apps/myactiviti/unittest/activiti-self-defined.cfg.xml";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule(CONFIG_PATH);

	@Test
	@Deployment(resources = { "com/lejingw/apps/myactiviti/unittest/process/myprocess.bpmn20.xml" })
	public void test1() {
		TaskService taskService = activitiRule.getTaskService();
		RuntimeService runtimeService = activitiRule.getRuntimeService();

		ProcessInstance processInstance = activitiRule.getRuntimeService()
				.startProcessInstanceByKey("my-process");
		assertNotNull(processInstance.getProcessDefinitionId());
		assertEquals(1, runtimeService.createProcessInstanceQuery().count());

		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("Activiti is awesome!", task.getName());

		taskService.complete(task.getId());

		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

	@Test
	public void test2() {
		deployProcessDefinition();

		startProcessInstance();

		dealWithTasks();
		
		suspendProcessDefinition();
	}
	
	public void test3(){
		/**
		 * 1. Window->Show View->Other and select Display
		 * 2. type command:org.h2.tools.Server.createWebServer("-web").start()
		 * 3. select the line you've just typed and right-click on it then select 'Display'
		 * 4. visite "http://localhost:8082/"
		 * 5. the h2 JDBC URL to the in-memory database by default this is jdbc:h2:mem:activiti
		 */
	}
	
	private void deployProcessDefinition() {
		// ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ProcessEngine processEngine = activitiRule.getProcessEngine();

		// RepositoryService repositoryService = processEngine.getRepositoryService();

		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/lejingw/apps/myactiviti/unittest/process/vacationRequest.bpmn20.xml")
				.deploy();
		
		assertEquals(1, repositoryService.createProcessDefinitionQuery()
				.count());

	}

	private void startProcessInstance() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");

		RuntimeService runtimeService = activitiRule.getRuntimeService();
		runtimeService.startProcessInstanceByKey("vacationRequest", variables);

		// Verify that we started a new process instance
		assertEquals(1, runtimeService.createProcessInstanceQuery().count());
	}

	private void dealWithTasks() {
		// Fetch all tasks for the management group
		TaskService taskService = activitiRule.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		for (Task task : tasks) {
			System.out.println("Task available: " + task.getName());
		}

		Task task = tasks.get(0);

		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		
		taskService.complete(task.getId(), taskVariables);
	}
	
	private void suspendProcessDefinition(){
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		
		//starting a process instance will thow a exception when the process definition is suspended
		repositoryService.suspendProcessDefinitionByKey("vacationRequest");
		try {
			runtimeService.startProcessInstanceByKey("vacationRequest");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof org.activiti.engine.ActivitiException);
		}
	}
}
