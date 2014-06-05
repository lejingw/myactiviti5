package com.lejingw.apps.myactiviti.deploy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;

public class DeployProcessDefinitionTest {
	@Rule
	private ActivitiRule activitiRule;

	// @Test
	public void deployProcessDefinition() throws FileNotFoundException {
		String barFileName = "path/to/process-one.bar";
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(
				barFileName));

		activitiRule.getRepositoryService().createDeployment()
				.name("process-one.bar").addZipInputStream(inputStream)
				.deploy();
	}

	public void deployProcessDefinition2() {
		activitiRule.getRepositoryService().createDeployment()
				.name("expense-process.bar")
				.addClasspathResource("org/activiti/expenseProcess.bpmn20.xml")
				.addClasspathResource("org/activiti/expenseProcess.png")
				.deploy();
	}

	public void getImageResource() {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionKey("expense")
				.singleResult();

		String diagramResourceName = processDefinition.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), diagramResourceName);

	}
}
