package com.buyden.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.buyden.customer.domain.enumeration.AddressType;

import com.buyden.customer.domain.enumeration.AddressStatus;

/**
 * A CustomerAddress.
 */
@Entity
@Table(name = "customer_address")
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private AddressStatus state;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerAddresses", allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerAddresses", allowSetters = true)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public CustomerAddress type(AddressType type) {
        this.type = type;
        return this;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public AddressStatus getState() {
        return state;
    }

    public CustomerAddress state(AddressStatus state) {
        this.state = state;
        return this;
    }

    public void setState(AddressStatus state) {
        this.state = state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerAddress customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public CustomerAddress address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerAddress)) {
            return false;
        }
        return id != null && id.equals(((CustomerAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerAddress{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
