package com.morgan.transacciones.service.client;

import com.morgan.transacciones.entity.Movimiento;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("movimientos")
public interface MovimientosFeignClient {

    @PostMapping("/api/crear")
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody Movimiento movimiento);
}
