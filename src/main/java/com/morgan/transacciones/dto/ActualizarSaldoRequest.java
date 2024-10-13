package com.morgan.transacciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ActualizarSaldoRequest {
    private Long id;
    private Double saldo;
    // getters y setters
}
