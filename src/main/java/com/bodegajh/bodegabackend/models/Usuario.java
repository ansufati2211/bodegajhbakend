package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.Normalizer;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String contrasena;

    // Seguimos exponiendo idRol como columna simple (lo usan UsuarioController, DTOs, etc.)
    @Column(name = "id_rol")
    private Integer idRol;

    // 👇 NUEVO: relación real hacia la tabla roles, solo lectura,
    // para poder derivar el authority dinámicamente en vez de hardcodearlo.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    private Rol rol;

    @Column(columnDefinition = "boolean default true")
    private Boolean estado;

    private static final Pattern DIACRITICOS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    // --- GETTERS Y SETTERS ---
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    // --- LOS 6 MÉTODOS OBLIGATORIOS DE SEGURIDAD (USERDETAILS) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Antes: siempre devolvía ROLE_ADMIN sin importar el rol real.
        // Ahora: lee el nombre real de la tabla roles (Dueño, Cajero, ...)
        // y lo convierte en un authority tipo ROLE_DUENO / ROLE_CAJERO.
        if (rol == null || rol.getNombre() == null) {
            // Usuario sin rol asignado: se le da el mínimo privilegio posible.
            return List.of(new SimpleGrantedAuthority("ROLE_SIN_ROL"));
        }
        String nombreNormalizado = quitarTildes(rol.getNombre())
                .trim()
                .toUpperCase()
                .replace(" ", "_");
        return List.of(new SimpleGrantedAuthority("ROLE_" + nombreNormalizado));
    }

    private static String quitarTildes(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return DIACRITICOS.matcher(normalizado).replaceAll("");
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado != null ? this.estado : true;
    }
}