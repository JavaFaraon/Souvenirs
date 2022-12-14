package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Souvenir {
    private String souvenirName;
    private double souvenirPrice;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfManufacturing;
    private ManufacturerInterface manufacturer;

    @Override
    public String toString() {
        return "Souvenir - " + souvenirName +
                ", Price: " + souvenirPrice +
                ", date of manufacturing: " + dateOfManufacturing +
                ", manufacturer: " + manufacturer;
    }
}
