package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.AddressSaveRequest;
import tr.edu.marmara.petcare.dto.AddressUpdateRequest;
import tr.edu.marmara.petcare.model.Address;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.model.Veterinary;
import tr.edu.marmara.petcare.repository.AddressRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(UUID id) {
        return addressRepository.findById(id).orElseThrow();
    }

    public void saveAddress(User veterinary, AddressSaveRequest addressSaveRequest) {
        Address address = Address.builder()
                .apartmentNo(addressSaveRequest.apartmentNo())
                .clinicDistrict(addressSaveRequest.clinicDistrict())
                .clinicName(addressSaveRequest.clinicName())
                .clinicNo(addressSaveRequest.clinicNo())
                .clinicStreet(addressSaveRequest.clinicStreet())
                .phoneNumber(addressSaveRequest.phoneNumber())
                .clinicCity(addressSaveRequest.clinicCity())
                .veterinary(veterinary)
                .build();
        addressRepository.save(address);
    }

    public Address updateAddress(UUID oldAddressId, AddressUpdateRequest updatedAddressRequest) {
        Address oldAddress = addressRepository.findById(oldAddressId)
                .orElseThrow();
        modelMapper.map(updatedAddressRequest, oldAddress);
        return addressRepository.save(oldAddress);
    }

    public Address deleteAddress(UUID id) {
        Address addressToBeDeleted = addressRepository.findById(id).
                orElseThrow();
        addressRepository.deleteById(id);
        return addressToBeDeleted;
    }
}
