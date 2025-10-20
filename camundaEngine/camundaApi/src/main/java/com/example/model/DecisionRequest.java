package com.example.model;

public class DecisionRequest {
  private String requestId;
  private String decision;
  private String businessKey;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  @Override
  public String toString() {
    return "DecisionRequest [getRequestId()=" + getRequestId() + ", getDecision()=" + getDecision()
        + ", getBusinessKey()=" + getBusinessKey() + "]";
  }

}
