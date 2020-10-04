package com.buyden.customer.service;

import com.buyden.customer.domain.PaymentMethod;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PaymentMethod}.
 */
public interface PaymentMethodService {

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    PaymentMethod save(PaymentMethod paymentMethod);

    /**
     * Get all the paymentMethods.
     *
     * @return the list of entities.
     */
    List<PaymentMethod> findAll();


    /**
     * Get the "id" paymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentMethod> findOne(Long id);

    /**
     * Delete the "id" paymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
