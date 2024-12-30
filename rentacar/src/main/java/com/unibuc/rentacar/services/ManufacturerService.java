package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));
        return Optional.ofNullable(manufacturer);
    }

    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public Manufacturer updateManufacturer(Integer id, Manufacturer manufacturerDetails) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));

        manufacturer.setName(manufacturerDetails.getName());
        manufacturer.setCountry(manufacturerDetails.getCountry());

        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }
}
