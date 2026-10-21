package com.scc.Management.model;

public class Pet {
    private int id;
    private String petName;
    private String species;
    private String vaccine;
    private double fee;

    public Pet(String petName, String species, String vaccine, double fee) {
        this.petName = petName;
        this.species = species;
        this.vaccine = vaccine;
        this.fee = fee;
    }

    public Pet(int id, String petName, String species, String vaccine, double fee) {
        this.id = id;
        this.petName = petName;
        this.species = species;
        this.vaccine = vaccine;
        this.fee = fee;
    }

    public int getId() { return id; }
    public String getPetName() { return petName; }
    public String getSpecies() { return species; }
    public String getVaccine() { return vaccine; }
    public double getFee() { return fee; }
}