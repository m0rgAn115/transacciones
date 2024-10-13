package com.morgan.transacciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Column(name = "clabe_emisor")
    @Pattern(regexp = "\\d{18}", message = "La CLABE debe contener exactamente 18 dígitos numéricos.")
    private String clabeEmisor;

    @Column(name = "clabe_receptor")
    @Pattern(regexp = "\\d{18}", message = "La CLABE debe contener exactamente 18 dígitos numéricos.")
    private String clabeReceptor;

    @NotNull
    private String concepto;

    private String descripcion;

    @NotNull
    private Double monto;

    @NotNull
    @Pattern(regexp = "^(Transferencia|Retiro|Deposito|Pago|Compra)$", message = "El tipo debe ser 'Transferencia', 'Retiro', 'Deposito' o 'Pago'.")
    private String tipo;

    @NotNull
    private boolean estatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", updatable = false)
    private Date fecha;
}