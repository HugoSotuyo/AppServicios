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
public class Proveedor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String servicio;
    private Double calificacion;
    private String remuneracion;
    private Double cantTrabajos;
    private String descripcion;
    private String email;
    private String password;  
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;

    public Proveedor() {
    }

    public Proveedor(String id, String nombre, String apellido, String direccion, String servicio, Double calificacion, String remuneracion, Double cantTrabajos, String descripcion, String email, String password, Rol rol, Imagen imagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.servicio = servicio;
        this.calificacion = calificacion;
        this.remuneracion = remuneracion;
        this.cantTrabajos = cantTrabajos;
        this.descripcion = descripcion;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.imagen = imagen;
    }

    public String getRemuneracion() {
        return remuneracion;
    }

    public void setRemuneracion(String remuneracion) {
        this.remuneracion = remuneracion;
    }

    public Double getCantTrabajos() {
        return cantTrabajos;
    }

    public void setCantTrabajos(Double cantTrabajos) {
        this.cantTrabajos = cantTrabajos;
    }

    

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    
    

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    
    
}
