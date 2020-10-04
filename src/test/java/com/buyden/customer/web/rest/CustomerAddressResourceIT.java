package com.buyden.customer.web.rest;

import com.buyden.customer.CustomerApp;
import com.buyden.customer.domain.CustomerAddress;
import com.buyden.customer.repository.CustomerAddressRepository;
import com.buyden.customer.service.CustomerAddressService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.buyden.customer.domain.enumeration.AddressType;
import com.buyden.customer.domain.enumeration.AddressStatus;
/**
 * Integration tests for the {@link CustomerAddressResource} REST controller.
 */
@SpringBootTest(classes = CustomerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerAddressResourceIT {

    private static final AddressType DEFAULT_TYPE = AddressType.DELIVERY;
    private static final AddressType UPDATED_TYPE = AddressType.BILLING;

    private static final AddressStatus DEFAULT_STATE = AddressStatus.ACTIVE;
    private static final AddressStatus UPDATED_STATE = AddressStatus.INACTIVE;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerAddressMockMvc;

    private CustomerAddress customerAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAddress createEntity(EntityManager em) {
        CustomerAddress customerAddress = new CustomerAddress()
            .type(DEFAULT_TYPE)
            .state(DEFAULT_STATE);
        return customerAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAddress createUpdatedEntity(EntityManager em) {
        CustomerAddress customerAddress = new CustomerAddress()
            .type(UPDATED_TYPE)
            .state(UPDATED_STATE);
        return customerAddress;
    }

    @BeforeEach
    public void initTest() {
        customerAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAddress() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();
        // Create the CustomerAddress
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isCreated());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustomerAddress.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createCustomerAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();

        // Create the CustomerAddress with an existing ID
        customerAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerAddresses() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get all the customerAddressList
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", customerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerAddress.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerAddress() throws Exception {
        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressService.save(customerAddress);

        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // Update the customerAddress
        CustomerAddress updatedCustomerAddress = customerAddressRepository.findById(customerAddress.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerAddress are not directly saved in db
        em.detach(updatedCustomerAddress);
        updatedCustomerAddress
            .type(UPDATED_TYPE)
            .state(UPDATED_STATE);

        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerAddress)))
            .andExpect(status().isOk());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustomerAddress.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAddress() throws Exception {
        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressService.save(customerAddress);

        int databaseSizeBeforeDelete = customerAddressRepository.findAll().size();

        // Delete the customerAddress
        restCustomerAddressMockMvc.perform(delete("/api/customer-addresses/{id}", customerAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
