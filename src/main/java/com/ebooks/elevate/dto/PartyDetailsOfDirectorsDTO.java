package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyDetailsOfDirectorsDTO {
    private String name;
    private String designation;
    private String phone; 
    private String email;

}
