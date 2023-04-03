package com.example.netchb.controller;

import com.example.netchb.entity.Manufacturer;
import com.example.netchb.dto.ManufacturerDto;
import com.example.netchb.repository.ManufacturerRepository;
import com.example.netchb.service.MessageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ManufacturerController {
    private final static Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelMapper modelMapper;

    private static final String HOME_PAGE_NAME = "Home";

    private static final String MANUFACTURER_VIEW_PAGE_NAME = "Manufacturers";

    private static final String INFO_MESSAGE_NAME = "infoMessage";

    private static final String MANUFACTURER_TABLE_NAME = "manufacturers";

    @GetMapping(path = "/")
    public String indexPage() {
        return HOME_PAGE_NAME;
    }

    @PostMapping(path = "/add")
    public String addNewManufacturer(@RequestBody Manufacturer manufacturer, Model model) {
        manufacturerRepository.save(manufacturer);
        logger.debug("Added manufacturer code:" + manufacturer.getManufacturerCode());

        model.addAttribute(INFO_MESSAGE_NAME, "Added manufacturer with code:" + manufacturer.getManufacturerCode());
        return HOME_PAGE_NAME;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile multipartFile, Model model) {
        logger.info("Uploaded file:" + multipartFile.getOriginalFilename() + ", length:" + multipartFile.getSize());

        ManufacturerDto manufacturer = messageService.converterFileToManufacturer(multipartFile);

        manufacturerRepository.save(modelMapper.map(manufacturer, Manufacturer.class));
        logger.info("Stored a new manufacturer with id code:" + manufacturer.getManufacturerCode());

        model.addAttribute(INFO_MESSAGE_NAME, "Uploaded file:" + multipartFile.getOriginalFilename() + ", length:" + multipartFile.getSize() + ", with manufacturer code id: " + manufacturer.getManufacturerCode());
        return HOME_PAGE_NAME;
    }

    @GetMapping(path = "/view")
    public String getAllManufacturers(Model model) {
        model.addAttribute(MANUFACTURER_TABLE_NAME, modelMapper.map(manufacturerRepository.findAll(), new TypeToken<List<ManufacturerDto>>() {
        }.getType()));
        return MANUFACTURER_VIEW_PAGE_NAME;
    }
}
