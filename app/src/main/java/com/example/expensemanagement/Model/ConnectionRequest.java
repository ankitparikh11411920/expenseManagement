package com.example.expensemanagement.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequest {
    private String _id;
    private String from;
    private String fromName;
    private String toName;
    private String to;
    private boolean isPending;
    
}
