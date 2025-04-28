package com.example.demoSpringSec.controllers;

import com.example.demoSpringSec.dto.VehicleRequest;
import com.example.demoSpringSec.models.Vehicle;
import com.example.demoSpringSec.repository.VehicleRepository;
import com.example.demoSpringSec.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @PostMapping("/add")
    public ResponseEntity<Vehicle> add(@RequestBody VehicleRequest vehicle){
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
    }
    @PostMapping("/add-vehicles")
    public void addMany(@RequestBody List<Vehicle> vehicles){
        vehicleService.addVehicles(vehicles);
    }
    @DeleteMapping("/delete")
    public void deleteVehicle(@RequestParam Long id){
        vehicleService.deleteVehicle(id);
    }
    @GetMapping("/all")
    public List<Vehicle> getVehicles(){
        return vehicleService.getAllVehicles();
    }
    @GetMapping("/search/model")
    public List<Vehicle> searchByModel(@RequestParam String model) {
        return vehicleRepository.findByModelContainingIgnoreCase(model);
    }

    @GetMapping("/search/plate")
    public Vehicle searchByPlateNumber(@RequestParam String plateNumber) {
        return vehicleRepository.findByPlateNumber(plateNumber);
    }

    @GetMapping("/search/year")
    public List<Vehicle> searchByYear(@RequestParam String year) {
        return vehicleRepository.findByYear(year);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferOwnership(
            @RequestParam Long vehicleId,
            @RequestParam Long newOwnerId
    ){
        vehicleService.transferOwnership(vehicleId,newOwnerId);
        return ResponseEntity.ok("Ownership transferred successfully");
    }
}
