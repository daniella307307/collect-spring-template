package com.example.demoSpringSec.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Data
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String manufacturer;
    private String model;
    private String year;
    private String plateNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
