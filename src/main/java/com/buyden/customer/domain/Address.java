package com.buyden.customer.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_1")
    private String line1;

    @Column(name = "line_2")
    private String line2;

    @Column(name = "line_3")
    private String line3;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "address")
    private Set<CustomerAddress> customerAddresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public Address line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public Address line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public Address line3(String line3) {
        this.line3 = line3;
        return this;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public Address zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public Address customerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
        return this;
    }

    public Address addCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.add(customerAddress);
        customerAddress.setAddress(this);
        return this;
    }

    public Address removeCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.remove(customerAddress);
        customerAddress.setAddress(null);
        return this;
    }

    public void setCustomerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", line3='" + getLine3() + "'" +
            ", zipcode=" + getZipcode() +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
