package com.gokhan.startupheroes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeliveredOrder {

    private Long id;

    private String createdAt;

    private String lastUpdatedAt;

    private Integer collectionDuration;

    private Integer deliveryDuration;

    private Integer eta;

    private Integer leadTime;

    private Boolean orderInTime;

}