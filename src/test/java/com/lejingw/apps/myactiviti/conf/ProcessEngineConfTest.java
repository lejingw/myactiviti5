package com.lejingw.apps.myactiviti.conf;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Assert;
import org.junit.Test;

public class ProcessEngineConfTest {
	@Test
	public void test1() {
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP)
				  .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
				  //jdbc.url=jdbc:h2:file:~/testdb/myactiviti5;AUTO_SERVER=TRUE
				  .setJobExecutorActivate(true)
				  .buildProcessEngine();
		
		assertObjectNotNull(processEngine);
	}

	@Test
	public void test2(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		assertObjectNotNull(processEngine);
	}

	private void assertObjectNotNull(ProcessEngine processEngine) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		TaskService taskService = processEngine.getTaskService();
		ManagementService managementService = processEngine.getManagementService();
		IdentityService identityService = processEngine.getIdentityService();
		HistoryService historyService = processEngine.getHistoryService();
		FormService formService = processEngine.getFormService();

		Assert.assertNotNull(runtimeService);
		Assert.assertNotNull(repositoryService);
		Assert.assertNotNull(taskService);
		Assert.assertNotNull(managementService);
		Assert.assertNotNull(identityService);
		Assert.assertNotNull(historyService);
		Assert.assertNotNull(formService);
	}
}
