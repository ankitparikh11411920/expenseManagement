package com.example.expensemanagement.Validators;



public class FieldValidator implements Validator{

    private String field,value;
    public FieldValidator(String field, String value){
        this.field = field.trim();
        this.value = value.trim();
    }

    @Override
    public ValidationResult validate(){
        if(value==null || value.isEmpty()){
            return new ValidationResult(false,field+" cannot be empty");
        }
        return ValidationResult.OK;
    }
}
