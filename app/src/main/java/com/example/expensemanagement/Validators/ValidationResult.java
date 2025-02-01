package com.example.expensemanagement.Validators;



public class ValidationResult {
    private boolean isValid;
    private String response;

    public static final ValidationResult OK = new ValidationResult(true, "Success");

    public ValidationResult() {
    }

    public ValidationResult(boolean isValid, String response) {
        this.isValid = isValid;
        this.response = response;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
