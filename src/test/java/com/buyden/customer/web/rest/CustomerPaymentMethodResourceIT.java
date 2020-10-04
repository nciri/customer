package com.buyden.customer.web.rest;

import com.buyden.customer.CustomerApp;
import com.buyden.customer.domain.CustomerPaymentMethod;
import com.buyden.customer.repository.CustomerPaymentMethodRepository;
import com.buyden.customer.service.CustomerPaymentMethodService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.buyden.customer.domain.enumeration.CardStatus;
/**
 * Integration tests for the {@link CustomerPaymentMethodResource} REST controller.
 */
@SpringBootTest(classes = CustomerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentMethodResourceIT {

    private static final CardStatus DEFAULT_STATUS = CardStatus.ACTIVE;
    private static final CardStatus UPDATED_STATUS = CardStatus.INACTIVE;

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentMethodMockMvc;

    private CustomerPaymentMethod customerPaymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentMethod createEntity(EntityManager em) {
        CustomerPaymentMethod customerPaymentMethod = new CustomerPaymentMethod()
            .status(DEFAULT_STATUS)
            .updatedAt(DEFAULT_UPDATED_AT);
        return customerPaymentMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentMethod createUpdatedEntity(EntityManager em) {
        CustomerPaymentMethod customerPaymentMethod = new CustomerPaymentMethod()
            .status(UPDATED_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);
        return customerPaymentMethod;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentMethodRepository.findAll().size();
        // Create the CustomerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(post("/api/customer-payment-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentMethod)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethodList = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentMethod testCustomerPaymentMethod = customerPaymentMethodList.get(customerPaymentMethodList.size() - 1);
        assertThat(testCustomerPaymentMethod.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerPaymentMethod.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createCustomerPaymentMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentMethodRepository.findAll().size();

        // Create the CustomerPaymentMethod with an existing ID
        customerPaymentMethod.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentMethodMockMvc.perform(post("/api/customer-payment-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentMethod)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethodList = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentMethods() throws Exception {
        // Initialize the database
        customerPaymentMethodRepository.saveAndFlush(customerPaymentMethod);

        // Get all the customerPaymentMethodList
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodRepository.saveAndFlush(customerPaymentMethod);

        // Get the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods/{id}", customerPaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerPaymentMethod() throws Exception {
        // Get the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodService.save(customerPaymentMethod);

        int databaseSizeBeforeUpdate = customerPaymentMethodRepository.findAll().size();

        // Update the customerPaymentMethod
        CustomerPaymentMethod updatedCustomerPaymentMethod = customerPaymentMethodRepository.findById(customerPaymentMethod.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentMethod are not directly saved in db
        em.detach(updatedCustomerPaymentMethod);
        updatedCustomerPaymentMethod
            .status(UPDATED_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomerPaymentMethodMockMvc.perform(put("/api/customer-payment-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerPaymentMethod)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethodList = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentMethod testCustomerPaymentMethod = customerPaymentMethodList.get(customerPaymentMethodList.size() - 1);
        assertThat(testCustomerPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerPaymentMethod.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentMethodRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentMethodMockMvc.perform(put("/api/customer-payment-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentMethod)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethodList = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodService.save(customerPaymentMethod);

        int databaseSizeBeforeDelete = customerPaymentMethodRepository.findAll().size();

        // Delete the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(delete("/api/customer-payment-methods/{id}", customerPaymentMethod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentMethod> customerPaymentMethodList = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
