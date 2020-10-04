package com.buyden.customer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.buyden.customer.web.rest.TestUtil;

public class CustomerAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAddress.class);
        CustomerAddress customerAddress1 = new CustomerAddress();
        customerAddress1.setId(1L);
        CustomerAddress customerAddress2 = new CustomerAddress();
        customerAddress2.setId(customerAddress1.getId());
        assertThat(customerAddress1).isEqualTo(customerAddress2);
        customerAddress2.setId(2L);
        assertThat(customerAddress1).isNotEqualTo(customerAddress2);
        customerAddress1.setId(null);
        assertThat(customerAddress1).isNotEqualTo(customerAddress2);
    }
}
