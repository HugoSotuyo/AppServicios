package com.GrupoE.WebAppServicios.repositorios;

import com.GrupoE.WebAppServicios.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    @Query("SELECT i FROM Imagen i WHERE i.id = :idImagen")
    public Imagen buscarImagenPorId(@Param("idImagen")String idImagen);
}
