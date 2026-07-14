package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Cliente;
import com.bodegajh.bodegabackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        if (cliente.getNombres() != null && cliente.getApellidos() != null) {
            cliente.setNombreCompleto(cliente.getNombres() + " " + cliente.getApellidos());
        } else {
            cliente.setNombreCompleto("Cliente General");
        }
        return clienteRepository.save(cliente);
    }

    // NUEVO: Endpoint para Actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente detallesCliente) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombres(detallesCliente.getNombres());
        cliente.setApellidos(detallesCliente.getApellidos());
        cliente.setNombreCompleto(detallesCliente.getNombres() + " " + detallesCliente.getApellidos());
        cliente.setDni(detallesCliente.getDni());
        cliente.setTelefono(detallesCliente.getTelefono());
        cliente.setCorreo(detallesCliente.getCorreo());
        cliente.setEstado(detallesCliente.getEstado());
        
        // No tocamos la deuda ni la fecha de registro
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    // NUEVO: Endpoint para Borrado Lógico (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        cliente.setEstado(false); // Borrado lógico
        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }
}