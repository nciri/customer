package com.buyden.customer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.buyden.customer.web.rest.TestUtil;

public class CustomerPaymentMethodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentMethod.class);
        CustomerPaymentMethod customerPaymentMethod1 = new CustomerPaymentMethod();
        customerPaymentMethod1.setId(1L);
        CustomerPaymentMethod customerPaymentMethod2 = new CustomerPaymentMethod();
        customerPaymentMethod2.setId(customerPaymentMethod1.getId());
        assertThat(customerPaymentMethod1).isEqualTo(customerPaymentMethod2);
        customerPaymentMethod2.setId(2L);
        assertThat(customerPaymentMethod1).isNotEqualTo(customerPaymentMethod2);
        customerPaymentMethod1.setId(null);
        assertThat(customerPaymentMethod1).isNotEqualTo(customerPaymentMethod2);
    }
}
