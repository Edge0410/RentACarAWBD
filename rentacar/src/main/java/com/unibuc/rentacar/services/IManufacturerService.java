package com.unibuc.rentacar.services;


import com.unibuc.rentacar.entities.Manufacturer;

import java.util.List;

public interface IManufacturerService {
    List<Manufacturer> getAllManufacturers();

    Manufacturer getManufacturerById(Integer id);

    int createManufacturer(Manufacturer manufacturer);

    int updateManufacturer(Integer id, Manufacturer manufacturer);

    void deleteManufacturer(Integer id);
}
