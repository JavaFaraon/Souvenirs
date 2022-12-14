package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Manufacturer implements ManufacturerInterface {
    private String name;
    private String country;

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer - " + name +
                ", country: " + country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Manufacturer that = (Manufacturer) obj;
        return name.equals(that.name) &&
                country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
