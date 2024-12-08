package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ManufacturerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map database rows to Manufacturer objects
    private RowMapper<Manufacturer> rowMapper = (rs, rowNum) -> {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(rs.getInt("id"));
        manufacturer.setName(rs.getString("name"));
        manufacturer.setCountry(rs.getString("country"));
        return manufacturer;
    };

    public List<Manufacturer> findAll() {
        String sql = "SELECT * FROM manufacturers";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Manufacturer> findById(Integer id) {
        String sql = "SELECT * FROM manufacturers WHERE id = ?";
        List<Manufacturer> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.stream().findFirst();
    }

    public int save(Manufacturer manufacturer) {
        String sql = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        return jdbcTemplate.update(sql, manufacturer.getName(), manufacturer.getCountry());
    }

    public int update(Integer id, Manufacturer manufacturer) {
        String sql = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        return jdbcTemplate.update(sql, manufacturer.getName(), manufacturer.getCountry(), id);
    }

    public int deleteById(Integer id) {
        String sql = "DELETE FROM manufacturers WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
