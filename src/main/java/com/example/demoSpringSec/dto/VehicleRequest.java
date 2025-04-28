package com.example.demoSpringSec.dto;

import com.example.demoSpringSec.models.User;
import lombok.Data;

@Data
public class VehicleRequest {
    private Long id;
    private String manufacturer;
    private String model;
    private String year;
    private String plateNumber;
    private User owner;
}
