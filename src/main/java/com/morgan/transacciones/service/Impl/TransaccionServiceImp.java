package com.morgan.transacciones.service.Impl;

import com.morgan.transacciones.entity.Transaccion;
import com.morgan.transacciones.repository.TransaccionRepository;
import com.morgan.transacciones.service.ITransaccionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransaccionServiceImp implements ITransaccionService {

    private final TransaccionRepository transaccionRepository;

    @Override
    public Transaccion crearTransaccion(Transaccion transaccion) {
        Transaccion transaccion_creada = transaccionRepository.save(transaccion);
        return transaccion_creada;
    }

    @Override
    public Transaccion getTransaccionById(Long id) {
        return transaccionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaccion no encontrado")
        );
    }

    @Override
    public List<Transaccion> getTransaccionesPorClabeEmisor(String clabe) {
        return transaccionRepository.findByClabeEmisor(clabe);
    }

    @Override
    public List<Transaccion> getTransaccionesPorUsuarioId(Long id) {
        return List.of();
    }

    @Override
    public List<Transaccion> getTransaccionesPorClabe(String clabe) {
        return transaccionRepository.findByClabeEmisor(clabe);

    }

}

