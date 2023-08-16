

package com.GrupoE.WebAppServicios.repositorios;
import com.GrupoE.WebAppServicios.entidades.Trabajo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo,String>{
    @Query("SELECT t FROM Trabajo t WHERE t.id = :id AND t.solicitud = 'ACEPTADA' AND t.realizado= true")
    public Trabajo TrabajoRealizadosProveedor(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id AND t.solicitud = 'ACEPTADA' AND t.realizado= true")
    public List<Trabajo> RealizadosProveedor(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id AND t.solicitud = 'ACEPTADA' ")//
    public List<Trabajo> TodosProveedor(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.usuario.id = :id")
    public List<Trabajo> TodosUsuario(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id AND t.solicitud = '0' ")
    public List<Trabajo> Solicitudes(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.usuario.id = :idUser AND t.realizado = true AND t.calificacion = 0 AND t.solicitud = 'ACEPTADA'")
    public List<Trabajo> trabajoRealizadoNoCalificado(@Param("idUser")String idUsuario);
    
    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :idProveedor AND  t.realizado = false AND t.solicitud = 'ACEPTADA'")
    public List<Trabajo> TrabajoNoRealizado(@Param("idProveedor")String id);

     /* @Query("SELECT t FROM Trabajo t WHERE t.usuario.id = :id")
    public Trabajo buscarPorIdUsuario(@Param("id")String id);
    
    @Query("SELECT t FROM Trabajo t WHERE t.usuario.nombre = :nombre")
    public Trabajo buscarPorNombreUsuario(@Param("nombre")String nombre);
    
    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.nombre = :nombre")
    public Trabajo buscarPorProveedor(@Param("nombre")String nombre);
    
    */


}