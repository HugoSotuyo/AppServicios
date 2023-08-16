package com.GrupoE.WebAppServicios.repositorios;

import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarUsuarioPorEmail(@Param("email")String email);
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public Usuario buscarPorNombreUsuario(@Param("nombre")String nombre);
    @Query("SELECT u FROM Usuario u WHERE u.apellido = :apellido")
    public Usuario buscarPorApellidoUsuario(@Param("apellido")String apellido);
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public List<Usuario> buscarPorNombreUsuarios(@Param("nombre")String nombre);
    @Query("SELECT u FROM Usuario u WHERE u.apellido = :apellido")
    public List<Usuario> buscarPorApellidoUsuarios(@Param("apellido")String apellido);
    @Query("SELECT u FROM Usuario u WHERE u.direccion = :direccion")
    public List<Usuario> buscarPorDireccion(@Param("direccion")String direccion);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.rol = :rol")
    boolean existsByRol(@Param("rol") Rol rol);
}
