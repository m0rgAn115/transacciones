package com.morgan.transacciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta  {
    private Long id;

    private Long idUsuario;

    private String nombre;

    private String clabe;

    private String numero_cuenta;
    private String alias;

    private Double saldo;
    private String tipo;
    private boolean estatus;

}
