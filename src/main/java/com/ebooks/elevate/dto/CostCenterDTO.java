package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostCenterDTO {
private Long id;
private String dimensionType;
private String valueCode;
private String valueDescription;
private String createdBy;
private Long orgId;
private boolean active;

}
