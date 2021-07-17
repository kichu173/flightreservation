package com.example.flightreservation.service;

import com.example.flightreservation.dto.ReservationRequest;
import com.example.flightreservation.model.Reservation;

public interface ReservationService {
    Reservation bookFlight(ReservationRequest request);
}
