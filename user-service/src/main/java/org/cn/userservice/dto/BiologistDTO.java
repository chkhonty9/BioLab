package org.cn.userservice.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BiologistDTO extends PersonDTO {
    private String label;
    private AdminDTO admin;
}
