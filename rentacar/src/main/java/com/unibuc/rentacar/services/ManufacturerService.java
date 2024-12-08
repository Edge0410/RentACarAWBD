package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.repositories.ManufacturerRepository;
import com.unibuc.rentacar.services.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService implements IManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer getManufacturerById(Integer id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with ID: " + id));
    }

    @Override
    public int createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public int updateManufacturer(Integer id, Manufacturer manufacturer) {
        return manufacturerRepository.update(id, manufacturer);
    }

    @Override
    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }
}