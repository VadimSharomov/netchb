package com.example.netchb.service;

import com.example.netchb.dto.ManufacturerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public ManufacturerDto converterFileToManufacturer(MultipartFile multipartFile) {
        return new ManufacturerDto.ManufacturerDtoBuilder(multipartFile).build();
    }
}
