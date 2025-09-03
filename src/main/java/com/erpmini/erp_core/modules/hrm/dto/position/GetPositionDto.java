package com.erpmini.erp_core.modules.hrm.dto.position;

import lombok.Data;

@Data
public class GetPositionDto {
    private Long id;
    private String code;
    private String name;
    private int level;
}
