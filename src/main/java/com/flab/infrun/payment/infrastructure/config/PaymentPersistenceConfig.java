package com.flab.infrun.payment.infrastructure.config;

import com.flab.infrun.payment.infrastructure.persistence.PaymentJpaRepository;
import com.flab.infrun.payment.infrastructure.persistence.PaymentRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentPersistenceConfig {

    @Bean
    public PaymentRepositoryAdapter paymentRepositoryAdapter(
        final PaymentJpaRepository paymentJpaRepository
    ) {
        return new PaymentRepositoryAdapter(paymentJpaRepository);
    }
}
