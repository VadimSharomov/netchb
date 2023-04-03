package com.example.netchb.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

public class ManufacturerDto {

    private String manufacturerCode;


    private String countryCode;


    private String firmName;


    private String streetAddress;


    private String city;


    private String postalCode;

    public ManufacturerDto() {
    }

    public ManufacturerDto(String manufacturerCode, String countryCode, String firmName, String streetAddress, String city, String postalCode) {
        this.manufacturerCode = manufacturerCode;
        this.countryCode = countryCode;
        this.firmName = firmName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    private ManufacturerDto(ManufacturerDtoBuilder builder) {
        this.manufacturerCode = builder.manufacturerCode;
        this.countryCode = builder.countryCode;
        this.firmName = builder.firmName;
        this.streetAddress = builder.streetAddress;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
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
        ManufacturerDto that = (ManufacturerDto) o;
        return Objects.equals(manufacturerCode, that.manufacturerCode) && Objects.equals(countryCode, that.countryCode) && Objects.equals(firmName, that.firmName) && Objects.equals(streetAddress, that.streetAddress) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerCode, countryCode, firmName, streetAddress, city, postalCode);
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

    public static class ManufacturerDtoBuilder {
        private final String manufacturerCode;

        private final String countryCode;

        private final String firmName;

        private final String streetAddress;

        private final String city;

        private final String postalCode;

        public ManufacturerDtoBuilder(MultipartFile multipartFile) {
            String message;
            try {
                message = new String(multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            validationMessage(message);
            this.manufacturerCode = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.MF_CODE);
            this.countryCode = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.COUNTRY_CODE);
            this.firmName = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.FIRM_NAME);
            this.streetAddress = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.STREET_ADDRESS);
            this.city = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.CITY);
            this.postalCode = getFieldFromMessage(message, ManufacturerDto.ManufacturerFields.POSTAL_CODE);
            validationFields(this);
        }

        public ManufacturerDto build() {
            return new ManufacturerDto(this);
        }

        private static String getFieldFromMessage(String message, ManufacturerDto.ManufacturerFields field) {
            return message.substring(message.indexOf(field.getRowMarker()) - 1 + field.getStartPosition(),
                    message.indexOf(field.getRowMarker()) + field.getEndPosition()).trim();
        }

        //validation of existing row markers and their order in a file $1 > $2 > $3 > $4 > $5
        private static void validationMessage(String message) {
            isTrue(message.indexOf(ManufacturerDto.ManufacturerFields.MF_CODE.getRowMarker()) > 0 &&
                            message.indexOf(ManufacturerDto.ManufacturerFields.COUNTRY_CODE.getRowMarker()) > message.indexOf(ManufacturerDto.ManufacturerFields.MF_CODE.getRowMarker()) &&
                            message.indexOf(ManufacturerDto.ManufacturerFields.STREET_ADDRESS.getRowMarker()) > message.indexOf(ManufacturerDto.ManufacturerFields.COUNTRY_CODE.getRowMarker()) &&
                            message.indexOf(ManufacturerDto.ManufacturerFields.CITY.getRowMarker()) > message.indexOf(ManufacturerDto.ManufacturerFields.STREET_ADDRESS.getRowMarker()) &&
                            message.indexOf(ManufacturerDto.ManufacturerFields.POSTAL_CODE.getRowMarker()) > message.indexOf(ManufacturerDto.ManufacturerFields.CITY.getRowMarker()),
                    "Incorrect file format!");
        }

        private static void validationFields(ManufacturerDtoBuilder builder) {
            //list of all fields which should be mandatory
            hasText(builder.manufacturerCode, "Manufacturer ID Code is empty!");
            hasText(builder.firmName, "Firm Name is empty!");
            hasText(builder.countryCode, "Country Code is empty!");
        }
    }

    private static enum ManufacturerFields {
        MF_CODE("$1", 3, 17),
        COUNTRY_CODE("$2", 7, 8),
        FIRM_NAME("$2", 9, 78),
        STREET_ADDRESS("$3", 33, 75),
        CITY("$4", 54, 76),
        POSTAL_CODE("$5", 47, 56);
        private final String rowMarker;
        private final int startPosition;
        private final int endPosition;

        ManufacturerFields(String rowMarker, int startPosition, int endPosition) {
            this.rowMarker = rowMarker;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        public String getRowMarker() {
            return this.rowMarker;
        }

        public int getStartPosition() {
            return this.startPosition;
        }

        public int getEndPosition() {
            return this.endPosition;
        }
    }
}
