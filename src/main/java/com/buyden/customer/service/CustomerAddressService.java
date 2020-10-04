package com.buyden.customer.service;

import com.buyden.customer.domain.CustomerAddress;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerAddress}.
 */
public interface CustomerAddressService {

    /**
     * Save a customerAddress.
     *
     * @param customerAddress the entity to save.
     * @return the persisted entity.
     */
    CustomerAddress save(CustomerAddress customerAddress);

    /**
     * Get all the customerAddresses.
     *
     * @return the list of entities.
     */
    List<CustomerAddress> findAll();


    /**
     * Get the "id" customerAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerAddress> findOne(Long id);

    /**
     * Delete the "id" customerAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
