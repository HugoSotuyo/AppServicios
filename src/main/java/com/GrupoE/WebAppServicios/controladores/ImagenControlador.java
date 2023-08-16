package com.GrupoE.WebAppServicios.controladores;


import com.GrupoE.WebAppServicios.entidades.Imagen;
import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.servicios.ImagenServicio;
import com.GrupoE.WebAppServicios.servicios.ProveedorServicio;
import com.GrupoE.WebAppServicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ImagenServicio imagenServicio;
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]>imagenUsuario(@PathVariable String id){
        Usuario usuario = usuarioServicio.getOne(id);
        byte[] imagen = usuario.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imagen, headers,HttpStatus.OK);
    }
    
    @GetMapping("/perfilProveedor/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable String id) {
        // Lógica para cargar la imagen del proveedor por su nombre de imagen
        Proveedor proveedor = proveedorServicio.getOne(id);
        byte[] imagen = proveedor.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/fotoPerfil/{idImagen}")
    public ResponseEntity<byte[]> imagenPerfil(@PathVariable String idImagen) {
        // Lógica para cargar la imagen del proveedor por su nombre de imagen
        Imagen imagenn = imagenServicio.buscarImagenPorId(idImagen);
        byte[] imagen = imagenn.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
