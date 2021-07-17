package com.example.flightreservation.service.impl;

import com.example.flightreservation.dto.ReservationRequest;
import com.example.flightreservation.model.Flight;
import com.example.flightreservation.model.Passenger;
import com.example.flightreservation.model.Reservation;
import com.example.flightreservation.repository.FlightRepository;
import com.example.flightreservation.repository.PassengerRepository;
import com.example.flightreservation.repository.ReservationRepository;
import com.example.flightreservation.service.ReservationService;
import com.example.flightreservation.util.EmailUtil;
import com.example.flightreservation.util.PDFGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    PDFGenerator pdfGenerator;
    @Autowired
    EmailUtil emailUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
    @Value("${com.expmple.flighreservation.itinerary.dirpath}")
    private String ITINERARY_DIR;

    @Override
    @Transactional // good choice to use in business layer(services layer)
    public Reservation bookFlight(ReservationRequest request) {
        LOGGER.info("Inside bookFlight()");
        // Make Payment

        Long flightId = request.getFlightId();
        LOGGER.info("Fetching Flight for id: " + flightId);
        Flight flight = flightRepository.findById(flightId).get();

        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setMiddleName(request.getPassengerMiddleName());
        passenger.setPhone(request.getPassengerPhone());
        passenger.setEmail(request.getPassengerEmail());
        LOGGER.info("Saving the passenger: " + passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        reservation.setCheckedIn(false);

        LOGGER.info("Saving the reservation: " + reservation);
        Reservation savedReservation = reservationRepository.save(reservation);
        String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
        LOGGER.info("Generating the itinerary");
        pdfGenerator.generateItinerary(savedReservation, filePath);
//        LOGGER.info("Emailing the itinerary");
        //emailUtil.sendItinerary(passenger.getEmail(), filePath);
        return savedReservation;
    }
}
