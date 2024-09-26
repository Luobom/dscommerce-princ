package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Payment;

import java.time.Instant;

public record PaymentDTO(
        Long id,
        Instant moment
) {

        public PaymentDTO(Long id, Instant moment) {
            this.id = id;
            this.moment = moment;
        }

        public PaymentDTO(Payment entity) {
            this(entity.getId(), entity.getMoment());
        }

}
