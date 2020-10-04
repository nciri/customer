package com.buyden.customer.service.impl;

import com.buyden.customer.service.CustomerPaymentMethodService;
import com.buyden.customer.domain.CustomerPaymentMethod;
import com.buyden.customer.repository.CustomerPaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerPaymentMethod}.
 */
@Service
@Transactional
public class CustomerPaymentMethodServiceImpl implements CustomerPaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentMethodServiceImpl.class);

    private final CustomerPaymentMethodRepository customerPaymentMethodRepository;

    public CustomerPaymentMethodServiceImpl(CustomerPaymentMethodRepository customerPaymentMethodRepository) {
        this.customerPaymentMethodRepository = customerPaymentMethodRepository;
    }

    @Override
    public CustomerPaymentMethod save(CustomerPaymentMethod customerPaymentMethod) {
        log.debug("Request to save CustomerPaymentMethod : {}", customerPaymentMethod);
        return customerPaymentMethodRepository.save(customerPaymentMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerPaymentMethod> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerPaymentMethods");
        return customerPaymentMethodRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentMethod> findOne(Long id) {
        log.debug("Request to get CustomerPaymentMethod : {}", id);
        return customerPaymentMethodRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentMethod : {}", id);
        customerPaymentMethodRepository.deleteById(id);
    }
}
