package com.example.demoSpringSec.service;

import com.example.demoSpringSec.dto.VehicleRequest;
import com.example.demoSpringSec.models.User;
import com.example.demoSpringSec.models.Vehicle;
import com.example.demoSpringSec.repository.UserRepository;
import com.example.demoSpringSec.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    public Vehicle saveVehicle(VehicleRequest vehicle){
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setYear(vehicle.getYear());
        vehicle1.setManufacturer(vehicle.getManufacturer());
        vehicle1.setModel(vehicle.getModel());
        vehicle1.setPlateNumber(vehicle.getPlateNumber());
        vehicle1.setOwner(vehicle.getOwner());
        if(vehicleRepository.existsByPlateNumber(vehicle1.getPlateNumber())){
            throw new RuntimeException("Vehicle already exists in the system");
        }

        return vehicleRepository.save(vehicle1);
    }
    public void addVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            return;
        }

        List<String> plateNumbers = vehicles.stream()
                .map(Vehicle::getPlateNumber)
                .toList();

        List<String> existingPlateNumbers = vehicleRepository.findPlateNumbersIn(plateNumbers);
        Set<String> existingPlateNumberSet = new HashSet<>(existingPlateNumbers);

        List<Vehicle> vehiclesToSave = vehicles.stream()
                .filter(vehicle -> !existingPlateNumberSet.contains(vehicle.getPlateNumber()))
                .toList();

        if (!vehiclesToSave.isEmpty()) {
            vehicleRepository.saveAll(vehiclesToSave);
        }
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    public Vehicle transferOwnership(Long vehicleId,Long newOwnerId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(()-> new RuntimeException("Vehicle not found"));
        User newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(()-> new RuntimeException("New owner not found"));
        vehicle.setOwner(newOwner);
        return vehicleRepository.save(vehicle);
    }

}
