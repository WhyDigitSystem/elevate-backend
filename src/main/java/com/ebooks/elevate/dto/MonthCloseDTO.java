package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthCloseDTO {
	
    private Long orgId;
    private String client;
    private String clientCode;
    private String yearType;
    private String finYear;
    private String month;
    private String closedBy;

}
