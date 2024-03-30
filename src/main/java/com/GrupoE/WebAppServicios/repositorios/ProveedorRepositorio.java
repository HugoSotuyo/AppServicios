package com.GrupoE.WebAppServicios.repositorios;

import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor,String>{
    @Query("SELECT p FROM Proveedor p WHERE p.email = :email")
    public Proveedor buscarProveedorPorEmail(@Param("email")String email);
    @Query("SELECT p FROM Proveedor p WHERE p.nombre = :nombre")
    public Proveedor buscarPorNombreProveedor(@Param("nombre")String nombre);
    @Query("SELECT p FROM Proveedor p WHERE p.apellido = :apellido")
    public Proveedor buscarPorApellidoProveedor(@Param("apellido")String apellido);
    @Query("SELECT p FROM Proveedor p WHERE p.servicio = :servicio")
    public List<Proveedor> buscarPorNombreDescripcion(@Param("servicio")String servicio);
    /*@Query("SELECT p FROM Proveedor p WHERE p.apellido = :apellido")
    public List<Proveedor> buscarPorApellidoProveedor(@Param("apellido")String apellido);*/
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Proveedor p WHERE p.rol = :rol")
    boolean existsByRol(@Param("rol") Rol rol);
}
