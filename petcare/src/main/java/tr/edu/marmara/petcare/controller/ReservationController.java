package tr.edu.marmara.petcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ReservationSaveRequest;
import tr.edu.marmara.petcare.model.Reservation;
import tr.edu.marmara.petcare.service.ReservationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@RequestParam UUID userId) {
        return new ResponseEntity<>(reservationService.getReservationsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationsById(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reservationService.getReservationById(reservationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveReservation(@RequestBody ReservationSaveRequest reservationSaveRequest) {
        return new ResponseEntity<>(reservationService.saveReservation(reservationSaveRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<MessageResponse> deleteReservation(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reservationService.deleteReservation(reservationId), HttpStatus.OK);
    }
}
