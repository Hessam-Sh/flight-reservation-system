package com.blackshuck.bookFlight.controller;

import com.blackshuck.bookFlight.entity.*;
import com.blackshuck.bookFlight.service.AirlineService;
import com.blackshuck.bookFlight.service.AirportService;
import com.blackshuck.bookFlight.service.FlightService;
import com.blackshuck.bookFlight.service.TicketService;
import com.blackshuck.bookFlight.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class AdminController
{
    private final TicketService ticketService;

    private final FlightService flightService;

    private final AirportService airportService;

    private final AirlineService airlineService;

    @Autowired
    public AdminController(TicketService ticketService, FlightService flightService,
                           AirportService airportService, AirlineService airlineService)
    {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.airportService = airportService;
        this.airlineService = airlineService;
    }

    @GetMapping("/admin")
    public String showTicket(Model model)
    {
        TicketFormDate ticketFormDate = new TicketFormDate();

        ticketFormDate.setAirline(new Airline());

        ticketFormDate.setOriginAirport(new Airport());

        ticketFormDate.setDestinationAirport(new Airport());

        ticketFormDate.setFlight(new Flight());

        model.addAttribute("formData", ticketFormDate);

        return "admin";
    }

    @PostMapping("/admin/saveTicket")
    public String saveTicket(@ModelAttribute TicketFormDate ticketFormDate,
                             @RequestParam("logo") MultipartFile multipartFile, Model model)
    {
        airportService.addNew(ticketFormDate.getOriginAirport());

        airportService.addNew(ticketFormDate.getDestinationAirport());

        String fileName = "";

        if (!multipartFile.getOriginalFilename().equals(""))
        {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            ticketFormDate.getAirline().setLogoUrl(fileName);
        }

        Airline saveAirline = airlineService.addNew(ticketFormDate.getAirline());

        String uploadDir = "logos/airline/" + saveAirline.getAirlineId();

        try
        {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
        }

        int originAirportId = ticketFormDate.getOriginAirport().getAirportId();

        int destinationAirportId = ticketFormDate.getDestinationAirport().getAirportId();

        int airlineId = ticketFormDate.getAirline().getAirlineId();

        flightService.addNew(ticketFormDate.getFlight(), originAirportId, destinationAirportId, airlineId);

        return "redirect:/admin";
    }
}
