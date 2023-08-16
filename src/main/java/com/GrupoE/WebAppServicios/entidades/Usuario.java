package com.GrupoE.WebAppServicios.entidades;

import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    private String barrio;
    private String direccion;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;
    private Boolean activo;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String barrio, String direccion, String email, String password, Rol rol, Imagen imagen, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.barrio = barrio;
        this.direccion = direccion;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.imagen = imagen;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    
    
    
}
