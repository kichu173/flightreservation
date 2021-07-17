package com.example.flightreservation.controllers;

import com.example.flightreservation.dto.ReservationRequest;
import com.example.flightreservation.model.Flight;
import com.example.flightreservation.model.Reservation;
import com.example.flightreservation.repository.FlightRepository;
import com.example.flightreservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @RequestMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {
        LOGGER.info("Inside showCompleteReservation() invoked with flight id: "+ flightId);
        Flight flight = flightRepository.findById(flightId).get();
        modelMap.addAttribute("flight", flight);
        LOGGER.info("FLight is: "+ flight);
        return "completeReservation";
    }

    @RequestMapping(value = "/completeReservation", method = RequestMethod.POST)
    public String completeReservation(ReservationRequest request,ModelMap modelMap) {
        LOGGER.info("Inside completeReservation(): "+ request);
        Reservation reservation = reservationService.bookFlight(request);
        modelMap.addAttribute("msg","Reservation Created successfully with id is "+ reservation.getId());
        return "reservationConfirmation";
    }
}
