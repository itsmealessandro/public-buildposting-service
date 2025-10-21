package com.example.model;

public class DecisionRequest {
  private String requestId;
  private Decision decision;
  private String businessKey;

  public Decision getDecision() {
    return decision;
  }

  public void setDecision(Decision decision) {
    this.decision = decision;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  @Override
  public String toString() {
    return "DecisionRequest [requestId=" + requestId + ", decision=" + decision + ", businessKey=" + businessKey + "]";
  }

}
