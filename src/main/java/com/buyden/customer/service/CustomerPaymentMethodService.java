package com.buyden.customer.service;

import com.buyden.customer.domain.CustomerPaymentMethod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerPaymentMethod}.
 */
public interface CustomerPaymentMethodService {

    /**
     * Save a customerPaymentMethod.
     *
     * @param customerPaymentMethod the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentMethod save(CustomerPaymentMethod customerPaymentMethod);

    /**
     * Get all the customerPaymentMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerPaymentMethod> findAll(Pageable pageable);


    /**
     * Get the "id" customerPaymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentMethod> findOne(Long id);

    /**
     * Delete the "id" customerPaymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
