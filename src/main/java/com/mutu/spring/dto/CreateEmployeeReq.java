package com.mutu.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeReq {
    private String empNo;
    private String firstName;
    private String lastName;
    private String email;
}