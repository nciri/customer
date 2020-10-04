package com.buyden.customer.repository;

import com.buyden.customer.domain.CustomerPaymentMethod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentMethodRepository extends JpaRepository<CustomerPaymentMethod, Long> {
}
