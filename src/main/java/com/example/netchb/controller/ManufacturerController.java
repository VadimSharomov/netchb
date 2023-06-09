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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.util.Assert.isTrue;

@Controller
public class ManufacturerController {
    private final static Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${maximum.manufacturer.file.size:1000}")
    private int maximumManufacturerFileSize;

    private static final String HOME_PAGE = "Home";

    private static final String MANUFACTURER_PAGE = "Manufacturers";

    private static final String INFO_MESSAGE = "infoMessage";

    private static final String MANUFACTURER_TABLE = "manufacturers";

    @GetMapping(path = "/")
    public ModelAndView indexPage() {
        ModelAndView model = new ModelAndView("Home");
        model.addObject("maximumManufacturerFileSize", maximumManufacturerFileSize);
        return model;
    }

    @PostMapping(path = "/add")
    public String addNewManufacturer(@RequestBody Manufacturer manufacturer, Model model) {
        manufacturerRepository.save(manufacturer);
        logger.debug("Added manufacturer code:" + manufacturer.getManufacturerCode());

        model.addAttribute("maximumManufacturerFileSize", maximumManufacturerFileSize);
        model.addAttribute(INFO_MESSAGE, "Added manufacturer with code:" + manufacturer.getManufacturerCode());
        return HOME_PAGE;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile multipartFile, Model model) {
        logger.info("Uploaded file: " + multipartFile.getOriginalFilename() + ", length:" + multipartFile.getSize());
        isTrue(multipartFile.getSize() < maximumManufacturerFileSize, "File is too big: " + multipartFile.getSize() + " bytes! It should be less than " + maximumManufacturerFileSize + " bytes!");

        ManufacturerDto manufacturer = messageService.converterFileToManufacturer(multipartFile);

        manufacturerRepository.save(modelMapper.map(manufacturer, Manufacturer.class));
        logger.info("Stored a new manufacturer with id code:" + manufacturer.getManufacturerCode());

        model.addAttribute("maximumManufacturerFileSize", maximumManufacturerFileSize);
        model.addAttribute(INFO_MESSAGE, "Uploaded file:" + multipartFile.getOriginalFilename() + ", length:" + multipartFile.getSize() + ", with manufacturer code id: " + manufacturer.getManufacturerCode());
        return HOME_PAGE;
    }

    @GetMapping(path = "/view")
    public String getAllManufacturers(Model model) {
        model.addAttribute(MANUFACTURER_TABLE, modelMapper.map(manufacturerRepository.findAll(), new TypeToken<List<ManufacturerDto>>() {
        }.getType()));
        return MANUFACTURER_PAGE;
    }

    @GetMapping(path = "/list")
    public String getAllManufacturersDynamically(Model model) {
        model.addAttribute(MANUFACTURER_TABLE, modelMapper.map(manufacturerRepository.findAll(), new TypeToken<List<ManufacturerDto>>() {
        }.getType()));
        return MANUFACTURER_PAGE + " :: mf_table";
    }
}
