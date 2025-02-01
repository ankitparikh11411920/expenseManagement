package com.example.expensemanagement.Validators;

import java.util.Arrays;
import java.util.List;

public class ValidationsUtils {

    public static ValidationResult validate(List<Validator> validatorList){
        if(validatorList!=null && !validatorList.isEmpty()){
            for(Validator validator : validatorList){
                ValidationResult result = validator.validate();
                if(!result.isValid()){
                    return result;
                }
            }
        }
        return ValidationResult.OK;
    }

    public static ValidationResult validate(Validator ... validators){
        return validate(Arrays.asList(validators));
    }
}
