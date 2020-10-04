package com.buyden.customer.web.rest;

import com.buyden.customer.domain.CustomerPaymentMethod;
import com.buyden.customer.service.CustomerPaymentMethodService;
import com.buyden.customer.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.buyden.customer.domain.CustomerPaymentMethod}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentMethodResource.class);

    private static final String ENTITY_NAME = "customerCustomerPaymentMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentMethodService customerPaymentMethodService;

    public CustomerPaymentMethodResource(CustomerPaymentMethodService customerPaymentMethodService) {
        this.customerPaymentMethodService = customerPaymentMethodService;
    }

    /**
     * {@code POST  /customer-payment-methods} : Create a new customerPaymentMethod.
     *
     * @param customerPaymentMethod the customerPaymentMethod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentMethod, or with status {@code 400 (Bad Request)} if the customerPaymentMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-methods")
    public ResponseEntity<CustomerPaymentMethod> createCustomerPaymentMethod(@RequestBody CustomerPaymentMethod customerPaymentMethod) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentMethod : {}", customerPaymentMethod);
        if (customerPaymentMethod.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentMethod result = customerPaymentMethodService.save(customerPaymentMethod);
        return ResponseEntity.created(new URI("/api/customer-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-methods} : Updates an existing customerPaymentMethod.
     *
     * @param customerPaymentMethod the customerPaymentMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentMethod,
     * or with status {@code 400 (Bad Request)} if the customerPaymentMethod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-methods")
    public ResponseEntity<CustomerPaymentMethod> updateCustomerPaymentMethod(@RequestBody CustomerPaymentMethod customerPaymentMethod) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentMethod : {}", customerPaymentMethod);
        if (customerPaymentMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentMethod result = customerPaymentMethodService.save(customerPaymentMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerPaymentMethod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-methods} : get all the customerPaymentMethods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentMethods in body.
     */
    @GetMapping("/customer-payment-methods")
    public ResponseEntity<List<CustomerPaymentMethod>> getAllCustomerPaymentMethods(Pageable pageable) {
        log.debug("REST request to get a page of CustomerPaymentMethods");
        Page<CustomerPaymentMethod> page = customerPaymentMethodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-payment-methods/:id} : get the "id" customerPaymentMethod.
     *
     * @param id the id of the customerPaymentMethod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentMethod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-methods/{id}")
    public ResponseEntity<CustomerPaymentMethod> getCustomerPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentMethod : {}", id);
        Optional<CustomerPaymentMethod> customerPaymentMethod = customerPaymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentMethod);
    }

    /**
     * {@code DELETE  /customer-payment-methods/:id} : delete the "id" customerPaymentMethod.
     *
     * @param id the id of the customerPaymentMethod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-methods/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentMethod : {}", id);
        customerPaymentMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
