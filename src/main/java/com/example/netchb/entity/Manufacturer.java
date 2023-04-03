package com.example.netchb.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "manufacturerdetails")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "manufacturer_id_code", nullable = false)
    private String manufacturerCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "firm_name")
    private String firmName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    public Manufacturer() {
    }

    public Manufacturer(Long id, String manufacturerCode, String countryCode, String firmName, String streetAddress, String city, String postalCode) {
        this.id = id;
        this.manufacturerCode = manufacturerCode;
        this.countryCode = countryCode;
        this.firmName = firmName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(id, that.id) && Objects.equals(manufacturerCode, that.manufacturerCode) && Objects.equals(countryCode, that.countryCode) && Objects.equals(firmName, that.firmName) && Objects.equals(streetAddress, that.streetAddress) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturerCode, countryCode, firmName, streetAddress, city, postalCode);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "manufacturerCode='" + manufacturerCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", firmName='" + firmName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
