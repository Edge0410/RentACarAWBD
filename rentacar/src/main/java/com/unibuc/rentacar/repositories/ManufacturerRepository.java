package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
