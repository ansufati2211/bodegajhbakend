package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Cliente;
import com.bodegajh.bodegabackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        // Unimos los datos del frontend para cumplir con la Base de Datos
        if (cliente.getNombres() != null && cliente.getApellidos() != null) {
            cliente.setNombreCompleto(cliente.getNombres() + " " + cliente.getApellidos());
        } else {
            cliente.setNombreCompleto("Cliente General");
        }
        return clienteRepository.save(cliente);
    }
}