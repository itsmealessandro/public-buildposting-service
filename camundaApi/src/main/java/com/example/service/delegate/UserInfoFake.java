package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.example.model.UserInfo;

public class UserInfoFake implements JavaDelegate {

  public void execute(DelegateExecution execution) {

    UserInfo userInfoFake = new UserInfo();

    userInfoFake.setUsername("mariorossi");
    userInfoFake.setName("Mario");
    userInfoFake.setSurname("Rossi");
    userInfoFake.setTaxCode("RSSMRA90A01E897Z");
    userInfoFake.setAddress("Viale Gran Sasso 1");
    userInfoFake.setCity("L'Aquila");
    userInfoFake.setZipCode(67100);
    userInfoFake.setPhone("+39 0862 000 555");
    userInfoFake.setEmail("mariorossi@email.it");

    execution.setVariableLocal("user_info", userInfoFake);

  }
}
