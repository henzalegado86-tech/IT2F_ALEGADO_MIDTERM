package com.scc.Management.dao;

import com.scc.Management.model.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pet_vaccinations ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "pet_name TEXT NOT NULL, "
                + "species TEXT NOT NULL, "
                + "vaccine TEXT NOT NULL, "
                + "fee REAL NOT NULL)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not create table. " + e.getMessage());
        }
    }

    public boolean insertPet(Pet pet) {
        String sql = "INSERT INTO pet_vaccinations (pet_name, species, vaccine, fee) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pet.getPetName());
            ps.setString(2, pet.getSpecies());
            ps.setString(3, pet.getVaccine());
            ps.setDouble(4, pet.getFee());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ERROR: Could not save record. " + e.getMessage());
            return false;
        }
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, pet_name, species, vaccine, fee FROM pet_vaccinations ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pets.add(new Pet(
                        rs.getInt("id"),
                        rs.getString("pet_name"),
                        rs.getString("species"),
                        rs.getString("vaccine"),
                        rs.getDouble("fee")));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Could not read records. " + e.getMessage());
        }
        return pets;
    }
}
