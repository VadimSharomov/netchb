package com.example.netchb.service;

import com.example.netchb.dto.ManufacturerDto;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {
    ManufacturerDto converterFileToManufacturer(MultipartFile multipartFile);
}
