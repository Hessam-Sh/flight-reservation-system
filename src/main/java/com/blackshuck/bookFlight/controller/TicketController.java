package com.blackshuck.bookFlight.controller;

import com.blackshuck.bookFlight.entity.*;
import com.blackshuck.bookFlight.repository.BookingRepository;
import com.blackshuck.bookFlight.repository.TicketRepository;
import com.blackshuck.bookFlight.service.PDFService;
import com.blackshuck.bookFlight.service.TicketService;
import com.blackshuck.bookFlight.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TicketController
{
    private final UserService userService;

    private final TicketService ticketService;

    private final BookingRepository bookingRepository;

    private final TicketRepository ticketRepository;

    private final PDFService pdfService;

    @Autowired
    public TicketController(UserService userService, TicketService ticketService, BookingRepository bookingRepository,
                            TicketRepository ticketRepository, PDFService pdfService)
    {
        this.userService = userService;
        this.ticketService = ticketService;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.pdfService = pdfService;
    }

    @GetMapping("paymentGateway")
    public String showPaymentPage(HttpSession httpSession, Model model)
    {
        List<Passenger> passengers = (List<Passenger>) httpSession.getAttribute("passengers");

        List<String> reservedSeats = (List<String>) httpSession.getAttribute("reservedSeats");

        String seatClass = (String) httpSession.getAttribute("seatClass");

        int priceByClass = (int) httpSession.getAttribute("priceByClass");

        httpSession.setAttribute("passengers", passengers);

        model.addAttribute("reservedSeats", reservedSeats);

        model.addAttribute("priceByClass", priceByClass);

        model.addAttribute("seatClass", seatClass);

        return "payment-gateway";
    }

    @GetMapping("/cancelTicket")
    public String cancelTicket()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String currentName = authentication.getName();

            User user = userService.findByEmail(currentName);

            int id = user.getUserId();

            ticketService.cancelTicket(id);
        }

        return "index";
    }

    @PostMapping("confirmTicket")
    public String confirmTicket(@RequestParam("reservedSeats") List<String> reservedSeats,
                                @RequestParam("seatClass") String seatClass,
                                @RequestParam("priceByClass") int priceByClass, HttpSession httpSession,Model model)
    {

        List<Passenger> passengers = (List<Passenger>) httpSession.getAttribute("passengers");

        ticketService.addNewTickets(passengers, priceByClass, seatClass, reservedSeats);

        httpSession.setAttribute("passengers", passengers);

        return "redirect:/showTickets";
    }

    @GetMapping("showTickets")
    public String showTickets(HttpSession httpSession, Model model)
    {
        List<Passenger> passengers = (List<Passenger>) httpSession.getAttribute("passengers");

        List<Ticket> latestTickets = passengers.stream().map(p -> ticketRepository.findLatestTicket(p)
                .get(0)).collect(Collectors.toList());

        List<Booking> latestBookings = passengers.stream()
                .map(p -> bookingRepository.findConfirmedBookingsByPassengerOrdered(p)
                        .stream().findFirst().orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());

        System.out.println("latestBookings (showTickets):" + latestBookings);

        System.out.println("latestTickets (showTickets): " + latestTickets);

        httpSession.setAttribute("latestTickets", latestTickets);

        model.addAttribute("latestTickets", latestTickets);

        model.addAttribute("latestBookings", latestBookings);

        return "confirmedTicket";
    }

    @GetMapping("/download-ticket-pdf")
    public ResponseEntity<byte[]> downloadTicketPdf(HttpSession httpSession)
    {
        List<Ticket> tickets = (List<Ticket>) httpSession.getAttribute("latestTickets");

        byte[] pdfBytes = pdfService.generateTicketPdf(tickets);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tickets.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
    }
}
