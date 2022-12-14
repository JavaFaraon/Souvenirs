package org.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Manufacturer.class)
public interface ManufacturerInterface {
    String getCountry();
}
