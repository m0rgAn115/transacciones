package com.morgan.transacciones.controller;

import com.morgan.transacciones.dto.ActualizarSaldoRequest;
import com.morgan.transacciones.entity.Cuenta;
import com.morgan.transacciones.entity.Movimiento;
import com.morgan.transacciones.entity.Transaccion;
import com.morgan.transacciones.service.ITransaccionService;
import com.morgan.transacciones.service.client.CuentaFeingClient;
import com.morgan.transacciones.service.client.MovimientosFeignClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class TransaccionController {

    ITransaccionService transaccionService;
    MovimientosFeignClient movimientosClient;
    CuentaFeingClient cuentaFeingClient;

    @PostMapping("/transferencia")
    public ResponseEntity<Transaccion> realizarTransferencia(@Valid @RequestBody Transaccion transaccion) {

        try{
            Cuenta emisor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeEmisor()).getBody();
            Cuenta receptor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeReceptor()).getBody();

            if(emisor!=null && receptor!=null) {
                if(emisor.getSaldo()<transaccion.getMonto()){
                    throw new RuntimeException("Saldo de cuenta emisora insuficiente");
                }else {
                    Double nuevo_saldo_emisor = emisor.getSaldo()-transaccion.getMonto();
                    Double nuevo_saldo_receptor = receptor.getSaldo()+transaccion.getMonto();
                    System.out.println(emisor.getId());
                    System.out.println(receptor.getId());

                    try {
                        cuentaFeingClient.actualizarSaldo(new ActualizarSaldoRequest(emisor.getId(), nuevo_saldo_emisor));
                        cuentaFeingClient.actualizarSaldo(new ActualizarSaldoRequest(receptor.getId(), nuevo_saldo_receptor));
                    }catch (Exception e){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al actualizando saldo");
                    }

                    Transaccion creada = transaccionService.crearTransaccion(transaccion);

                    movimientosClient.crearMovimiento(new Movimiento(emisor.getIdUsuario(),emisor.getId(),transaccion.getTipo() ,transaccion.getMonto()*-1,creada.getId(),transaccion.getConcepto()));
                    movimientosClient.crearMovimiento(new Movimiento(receptor.getIdUsuario(),receptor.getId(),transaccion.getTipo() ,transaccion.getMonto(), creada.getId(),transaccion.getConcepto()));


                    return ResponseEntity.ok(transaccion);
                }

            }


        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

        transaccionService.crearTransaccion(transaccion);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaccion);
    }

    @PostMapping("/retiro")
    public ResponseEntity<Transaccion> realizarRetiro(@Valid @RequestBody Transaccion transaccion) {

//        try{
//            Cuenta emisor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeEmisor()).getBody();
//            Cuenta receptor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeReceptor()).getBody();
//
//            if(emisor!=null && receptor!=null) {
//                switch (transaccion.getTipo()){
//                    case "Transferencia": {
//                        cuentaFeingClient.actualizarSaldo(emisor.getId(),transaccion.getMonto());
//                        cuentaFeingClient.actualizarSaldo(emisor.getId(),transaccion.getMonto());
//                    }
//                    break;
//                    case "Retiro": {
//
//                    }
//                }
//
//
//            }
//
//
//        }catch(Exception e){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
//        }

        transaccionService.crearTransaccion(transaccion);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaccion);
    }

    @PostMapping("/compra")
    public ResponseEntity<Transaccion> realizarCompra(@Valid @RequestBody Transaccion transaccion) {

        try{
            Cuenta emisor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeEmisor()).getBody();

            if(emisor!=null) {
                if(emisor.getSaldo()<transaccion.getMonto()){
                    throw new RuntimeException("Saldo de cuenta emisora insuficiente");
                }else {
                    Double nuevo_saldo_emisor = emisor.getSaldo()-transaccion.getMonto();
                    try {
                        cuentaFeingClient.actualizarSaldo(new ActualizarSaldoRequest(emisor.getId(), nuevo_saldo_emisor));
                    }catch (Exception e){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al actualizando saldo");
                    }

                    Transaccion creada = transaccionService.crearTransaccion(transaccion);

                    movimientosClient.crearMovimiento(new Movimiento(emisor.getIdUsuario(),emisor.getId(),transaccion.getTipo() ,transaccion.getMonto()*-1,creada.getId(),transaccion.getConcepto()));

                    return ResponseEntity.ok(transaccion);
                }

            }


        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

        transaccionService.crearTransaccion(transaccion);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaccion);
    }

    @PostMapping("/deposito")
    public ResponseEntity<Transaccion> realizarDeposito(@Valid @RequestBody Transaccion transaccion) {

        try{
            Cuenta emisor = cuentaFeingClient.getCuentasByClabe(transaccion.getClabeEmisor()).getBody();

            if(emisor!=null) {
                    Double nuevo_saldo_emisor = emisor.getSaldo()+transaccion.getMonto();
                    try {
                        cuentaFeingClient.actualizarSaldo(new ActualizarSaldoRequest(emisor.getId(), nuevo_saldo_emisor));
                    }catch (Exception e){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al actualizando saldo");
                    }

                    Transaccion creada = transaccionService.crearTransaccion(transaccion);

                    movimientosClient.crearMovimiento(new Movimiento(emisor.getIdUsuario(),emisor.getId(),transaccion.getTipo() ,transaccion.getMonto()*-1,creada.getId(),transaccion.getConcepto()));

                    return ResponseEntity.ok(transaccion);
            }


        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

        transaccionService.crearTransaccion(transaccion);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaccion);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable("id") Long id) {
        Transaccion transaccion = transaccionService.getTransaccionById(id);
        return ResponseEntity.ok(transaccion);
    }

    @GetMapping("/get/usuario/{id}")
    public ResponseEntity<List<Transaccion>> getTransaccionesByUsuarioId(@PathVariable("id") Long idUsuario) {
        List<Transaccion> transacciones = transaccionService.getTransaccionesPorUsuarioId(idUsuario);
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/get/clabe/{id}")
    public ResponseEntity<List<Transaccion>> getCuentasByUsuarioID(@PathVariable("id") String clabe) {
        List<Transaccion> transaccions = transaccionService.getTransaccionesPorClabeEmisor(clabe);
        return ResponseEntity.ok(transaccions);
    }
}
