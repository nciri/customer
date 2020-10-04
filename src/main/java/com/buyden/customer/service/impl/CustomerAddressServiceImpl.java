package com.buyden.customer.service.impl;

import com.buyden.customer.service.CustomerAddressService;
import com.buyden.customer.domain.CustomerAddress;
import com.buyden.customer.repository.CustomerAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerAddress}.
 */
@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final Logger log = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);

    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressServiceImpl(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public CustomerAddress save(CustomerAddress customerAddress) {
        log.debug("Request to save CustomerAddress : {}", customerAddress);
        return customerAddressRepository.save(customerAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAddress> findAll() {
        log.debug("Request to get all CustomerAddresses");
        return customerAddressRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerAddress> findOne(Long id) {
        log.debug("Request to get CustomerAddress : {}", id);
        return customerAddressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerAddress : {}", id);
        customerAddressRepository.deleteById(id);
    }
}
