package com.morgan.transacciones.service.client;

import com.morgan.transacciones.dto.ActualizarSaldoRequest;
import com.morgan.transacciones.entity.Cuenta;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cuentas")
public interface CuentaFeingClient {

    @GetMapping("/api/get/clabe/{id}")
    public ResponseEntity<Cuenta> getCuentasByClabe(@PathVariable("id") String clabe);

    @PutMapping("/api/actualizar-saldo")
    public ResponseEntity<Void> actualizarSaldo(@RequestBody ActualizarSaldoRequest request);

}
