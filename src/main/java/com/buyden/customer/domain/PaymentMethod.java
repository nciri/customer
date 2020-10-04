package com.buyden.customer.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PaymentMethod.
 */
@Entity
@Table(name = "payment_method")
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "secret_code")
    private Integer secretCode;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<CustomerPaymentMethod> customerPaymentMethods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public PaymentMethod cardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public PaymentMethod cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getSecretCode() {
        return secretCode;
    }

    public PaymentMethod secretCode(Integer secretCode) {
        this.secretCode = secretCode;
        return this;
    }

    public void setSecretCode(Integer secretCode) {
        this.secretCode = secretCode;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public PaymentMethod dateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Set<CustomerPaymentMethod> getCustomerPaymentMethods() {
        return customerPaymentMethods;
    }

    public PaymentMethod customerPaymentMethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.customerPaymentMethods = customerPaymentMethods;
        return this;
    }

    public PaymentMethod addCustomerPaymentMethod(CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethods.add(customerPaymentMethod);
        customerPaymentMethod.setPaymentMethod(this);
        return this;
    }

    public PaymentMethod removeCustomerPaymentMethod(CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethods.remove(customerPaymentMethod);
        customerPaymentMethod.setPaymentMethod(null);
        return this;
    }

    public void setCustomerPaymentMethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.customerPaymentMethods = customerPaymentMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", cardType='" + getCardType() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", secretCode=" + getSecretCode() +
            ", dateEnd='" + getDateEnd() + "'" +
            "}";
    }
}
