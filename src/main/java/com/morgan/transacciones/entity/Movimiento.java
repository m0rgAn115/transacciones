package com.morgan.transacciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

    private long id;

    private Long idUsuario;

    private Long idCuenta;

    private Long idTransaccion;

    private String tipo;

    Double monto;

    private String descripcion;

    private Date fecha;

    public Movimiento(Long idUsuario, Long idCuenta, String tipo, Double monto, Long idTransaccion, String descripcion) {
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.tipo = tipo;
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
    }

}
