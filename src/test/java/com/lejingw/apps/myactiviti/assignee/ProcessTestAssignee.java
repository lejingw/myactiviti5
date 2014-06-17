package com.lejingw.apps.myactiviti.assignee;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestAssignee {
	private final String CONFIG_PATH = "com/lejingw/apps/myactiviti/unittest/activiti-self-defined.cfg.xml";

	private String filename = "/home/lejingw/workspaces/git_workspace/activititest/myactiviti5/src/main/resources/com/lejingw/apps/myactiviti/assignee/assigneeProcess.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule(CONFIG_PATH);

	/**
	 * TaskQuery 's taskCandidateOrAssigned method not like taskAssignee
	 * when a task is assigned to a specific user
	 */
	@Test
	public void taskCandidateOrAssignedAndTaskAssignee() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("assigneeProcess.bpmn20.xml", new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("userid", "lejingw");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("assigneeProcess", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());
		
		TaskService taskService = activitiRule.getTaskService();
		TaskQuery taskqurey = taskService.createTaskQuery().taskCandidateOrAssigned("lejingw");
		assertEquals(0, taskqurey.list().size());

		TaskQuery taskqurey2 = taskService.createTaskQuery().taskAssignee("lejingw");
		assertEquals(1, taskqurey2.list().size());
	}
}