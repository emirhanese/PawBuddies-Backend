package tr.edu.marmara.petcare.dto;

public record AddressSaveRequest(String phoneNumber,
         String clinicName,
         String clinicCity,
         String clinicStreet,
         String clinicDistrict,
         String clinicNo,
         String apartmentNo) {
}
