package com.buyden.customer.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "customer")
    private Set<CustomerPaymentMethod> customerPaymentMethods = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<CustomerAddress> customerAddresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Customer middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<CustomerPaymentMethod> getCustomerPaymentMethods() {
        return customerPaymentMethods;
    }

    public Customer customerPaymentMethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.customerPaymentMethods = customerPaymentMethods;
        return this;
    }

    public Customer addCustomerPaymentMethod(CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethods.add(customerPaymentMethod);
        customerPaymentMethod.setCustomer(this);
        return this;
    }

    public Customer removeCustomerPaymentMethod(CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethods.remove(customerPaymentMethod);
        customerPaymentMethod.setCustomer(null);
        return this;
    }

    public void setCustomerPaymentMethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.customerPaymentMethods = customerPaymentMethods;
    }

    public Set<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public Customer customerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
        return this;
    }

    public Customer addCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.add(customerAddress);
        customerAddress.setCustomer(this);
        return this;
    }

    public Customer removeCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.remove(customerAddress);
        customerAddress.setCustomer(null);
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
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
