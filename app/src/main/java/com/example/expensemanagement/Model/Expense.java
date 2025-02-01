package com.example.expensemanagement.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private String categoryName;
    private String amount;
    private String date;
    private String id;
    private String description;

}
