package com.example.netchb;

import com.example.netchb.dto.ManufacturerDto;
import com.example.netchb.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class MessageServiceTests {
    final static String MANUFACTURER_ID_CODE = "US20TCEN1020LOS";
    final static String COUNTRY_CODE = "US";
    final static String FIRM_NAME = "20TH CENTURY FOX TELEVISION";
    final static String STREET_ADDRESS = "10201 W. PICO BLVD";
    final static String CITY = "LOS ANGELES";
    final static String POSTAL_CODE = "90035";

    @Value("${maximum.manufacturer.file.size:1000}")
    private int maximumManufacturerFileSize;

    @Autowired
    MessageService messageService;

    @Test
    void testMultipartFileConvertToManufacturer() throws IOException {

        final String fileName = "sampleFile1.txt";
        final File file = new File("src/test/resources/" + fileName);

        assertTrue(file.exists(), " Test file is not existed: " + fileName);

        assertTrue(file.length() < maximumManufacturerFileSize, "File is too big: " + file.length() + " bytes! It should be less than " + maximumManufacturerFileSize + " bytes!");

        MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(file));
        ManufacturerDto manufacturer = messageService.converterFileToManufacturer(multipartFile);

        assertEquals(MANUFACTURER_ID_CODE, manufacturer.getManufacturerCode(), "Manufacturer Code Id is not correct!");
        assertEquals(COUNTRY_CODE, manufacturer.getCountryCode(), "Country Code is not correct!");
        assertEquals(FIRM_NAME, manufacturer.getFirmName(), "Firm Name is not correct!");
        assertEquals(STREET_ADDRESS, manufacturer.getStreetAddress(), "Street Address is not correct!");
        assertEquals(CITY, manufacturer.getCity(), "City is not correct!");
        assertEquals(POSTAL_CODE, manufacturer.getPostalCode(), "Postal Code is not correct!");
    }

    @Test
    void testMultipartFileConvertToManufacturerIncorrectFileFormat() throws IOException {
        final String fileName = "incorrectFileFormat.txt";
        final File file = new File("src/test/resources/" + fileName);

        assertTrue(file.exists(), " Test file is not existed: " + fileName);

        assertTrue(file.length() < maximumManufacturerFileSize, "File is too big: " + file.length() + " bytes! It should be less than " + maximumManufacturerFileSize + " bytes!");

        MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(file));
        Exception  exception = assertThrows(IllegalArgumentException.class, () -> messageService.converterFileToManufacturer(multipartFile));

        assertEquals("Incorrect file format!", exception.getMessage());
    }
}
