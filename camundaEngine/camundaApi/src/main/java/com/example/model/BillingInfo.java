package com.example.model;

public class BillingInfo {

  private String accountHolder;
  private String invoiceNumber;
  private String amountDue;

  public String getAccountHolder() {
    return accountHolder;
  }

  public void setAccountHolder(String accountHolder) {
    this.accountHolder = accountHolder;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getAmountDue() {
    return amountDue;
  }

  public void setAmountDue(String amountDue) {
    this.amountDue = amountDue;
  }

  @Override
  public String toString() {
    return "BillingInfo [accountHolder=" + accountHolder + ", invoiceNumber=" + invoiceNumber + ", amountDue="
        + amountDue + "]";
  }

}
