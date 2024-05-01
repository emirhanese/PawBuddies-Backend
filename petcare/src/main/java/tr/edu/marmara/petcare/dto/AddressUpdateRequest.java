package tr.edu.marmara.petcare.dto;

public record AddressUpdateRequest(String phoneNumber, String clinicName, String clinicCity,
                                   String clinicDistrict, String clinicStreet, String clinicNo,
                                   String apartmentNo) {
}
