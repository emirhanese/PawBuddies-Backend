package tr.edu.marmara.petcare.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ReservationSaveRequest;
import tr.edu.marmara.petcare.exception.ReservationNotFoundException;
import tr.edu.marmara.petcare.model.Reservation;
import tr.edu.marmara.petcare.repository.ReservationRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservationsByUserId(UUID userId) {
        return reservationRepository.findReservationsByReservationOwner(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
    }

    public Reservation getReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with given ID!"));
    }

    public MessageResponse saveReservation(ReservationSaveRequest reservationSaveRequest) {
        var reservation = Reservation.builder()
                .reservationDay(reservationSaveRequest.reservationDay())
                .reservationOwner(reservationSaveRequest.reservationOwner())
                .reservationTime(reservationSaveRequest.reservationTime())
                .pet(reservationSaveRequest.pet())
                .subject(reservationSaveRequest.subject())
                .veterinary(reservationSaveRequest.veterinary())
                .build();
        reservationRepository.save(reservation);
        return new MessageResponse("Reservation created successfully");
    }

    public MessageResponse deleteReservation(UUID reservationId) {
        reservationRepository.deleteById(reservationId);
        return new MessageResponse("Reservation deleted successfully");
    }
}
