package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.entities.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Car> rowMapper = (rs, rowNum) -> {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setManufacturerId(rs.getInt("manufacturer_id"));
        car.setModel(rs.getString("model"));
        car.setMileage(rs.getInt("mileage"));
        car.setFuelType(rs.getString("fuel_type"));
        car.setRentalPrice(rs.getDouble("rental_price"));
        return car;
    };

    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Car> findById(Integer id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        List<Car> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.stream().findFirst();
    }

    public int save(Car car) {
        String sql = "INSERT INTO cars (manufacturer_id, model, mileage, fuel_type, rental_price) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, car.getManufacturerId(), car.getModel(), car.getMileage(), car.getFuelType(), car.getRentalPrice());
    }

    public int update(Integer id, Car car) {
        String sql = "UPDATE cars SET manufacturer_id = ?, model = ?, mileage = ?, fuel_type = ?, rental_price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, car.getManufacturerId(), car.getModel(), car.getMileage(), car.getFuelType(), car.getRentalPrice(), id);
    }

    public int deleteById(Integer id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}