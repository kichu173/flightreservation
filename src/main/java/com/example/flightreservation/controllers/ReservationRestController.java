package com.example.flightreservation.controllers;

import com.example.flightreservation.dto.ReservationUpdateRequest;
import com.example.flightreservation.model.Reservation;
import com.example.flightreservation.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ReservationRestController {
    @Autowired
    private ReservationRepository reservationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);

    @RequestMapping("/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        LOGGER.info("Inside findReservation() for id: " + id);
        return reservationRepository.findById(id).get();
    }

    @PostMapping("/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
        LOGGER.info("Inside updateReservation() for " + request);
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        reservation.setNumberOfBags(request.getNumberOfBags());
        reservation.setCheckedIn(request.getCheckedIn());
        LOGGER.info("saving reservation");
        return reservationRepository.save(reservation);
    }
}
