package com.lejingw.apps.myactiviti.unittest;

import org.activiti.engine.RuntimeService;
import org.springframework.transaction.annotation.Transactional;

public class UserBean {

  /** injected by Spring */
  private RuntimeService runtimeService;

  @Transactional
  public void hello(String processDefinedId) {
    // here you can do transactional stuff in your domain model
    // and it will be combined in the same transaction as 
    // the startProcessInstanceByKey to the Activiti RuntimeService
    runtimeService.startProcessInstanceByKey(processDefinedId);
  }
  
  public void setRuntimeService(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }
}