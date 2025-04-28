package com.example.demoSpringSec.repository;

import com.example.demoSpringSec.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    boolean existsByPlateNumber(String plateNumber);
    @Query("SELECT v.plateNumber FROM Vehicle  v WHERE v.plateNumber IN :plateNumbers")
    List<String> findPlateNumbersIn(@Param("plateNumbers") List<String> plateNumbers);

    // Find by manufacturer
    List<Vehicle> findByManufacturerContainingIgnoreCase(String manufacturer);

    // Find by model
    List<Vehicle> findByModelContainingIgnoreCase(String model);

    // Find by plate number
    Vehicle findByPlateNumber(String plateNumber);

    // Find by year
    List<Vehicle> findByYear(String year);
}
