package com.GrupoE.WebAppServicios.servicios;

import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.entidades.Trabajo;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.repositorios.ProveedorRepositorio;
import com.GrupoE.WebAppServicios.repositorios.TrabajoRepositorio;
import com.GrupoE.WebAppServicios.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Transactional
    public void registrar(String idUsuario, String idProveedor, String descripcion) throws MyException {

        validar(idUsuario, idProveedor, descripcion);

        Optional<Usuario> user = usuarioRepositorio.findById(idUsuario);
        Trabajo trabajo = new Trabajo();

        if (user.isPresent()) {
            Usuario usuario = user.get();
            trabajo.setUsuario(usuario);
        }

        Optional<Proveedor> prov = proveedorRepositorio.findById(idProveedor);

        if (prov.isPresent()) {
            Proveedor proveedor = prov.get();
            trabajo.setProveedor(proveedor);

        }

        trabajo.setCalificacion(0.0);
        trabajo.setComentario("Sin comentario");
        trabajo.setRealizado(false);
        trabajo.setDescripcion(descripcion);
        trabajo.setSolicitud("0");
        trabajoRepositorio.save(trabajo);

    }

    @Transactional
    public void actualizar(String idTrabajo, String idUsuario, String idProveedor, boolean realizado, Double calificacion, String comentario, String descripcion) throws MyException {

        validarTrabajo(idTrabajo, idProveedor, idUsuario, descripcion, comentario);

        Optional<Trabajo> t = trabajoRepositorio.findById(idTrabajo);

        if (t.isPresent()) {
            Trabajo trabajo = t.get();

            Optional<Usuario> user = usuarioRepositorio.findById(idUsuario);
            if (user.isPresent()) {
                Usuario usuario = user.get();
                trabajo.setUsuario(usuario);
            }

            Optional<Proveedor> prov = proveedorRepositorio.findById(idProveedor);
            if (prov.isPresent()) {
                Proveedor proveedor = prov.get();
                trabajo.setProveedor(proveedor);

            }
            trabajo.setCalificacion(calificacion);
            trabajo.setComentario(comentario);
            trabajo.setRealizado(realizado);
            trabajo.setDescripcion(descripcion);
            trabajo.setSolicitud("0");

            trabajoRepositorio.save(trabajo);
        }

    }

    @Transactional
    public Trabajo getOne(String id) {
        return trabajoRepositorio.getOne(id);
    }

    @Transactional
    public void Eliminar(String id) throws MyException {
        Trabajo trabajo = trabajoRepositorio.getById(id);
        trabajoRepositorio.delete(trabajo);
    }

//LISTAS 
    @Transactional//(readOnly=True)
    public List<Trabajo> listarTrabajos() {

        List<Trabajo> trabajos = trabajoRepositorio.findAll();

        return trabajos;
    }

    @Transactional//(readOnly=True)
    public List<Trabajo> trabajosRealizadosNoCalificados(String idUsuario) {

        return trabajoRepositorio.trabajoRealizadoNoCalificado(idUsuario);
    }

    @Transactional//(readOnly=True)
    public List<Trabajo> TodosUsuario(String idUsuario) {
        //todos los trabajos a nombre de un ususario. Sirve para ver el estado de los trabajos solicitados ACEPTADO / RECHAZADO
        return trabajoRepositorio.TodosUsuario(idUsuario);
    }

    @Transactional//(readOnly=True)
    public List<Trabajo> Solicitudes(String idProveedor) {

        return trabajoRepositorio.Solicitudes(idProveedor);
    }

    @Transactional//(readOnly=True)
    public List<Trabajo> listarTrabajosNoRealizados(String idProveedor) {

        List<Trabajo> trabajosNoRealizados = trabajoRepositorio.TrabajoNoRealizado(idProveedor);

        return trabajosNoRealizados;
    }

    @Transactional//(readOnly=True)
    public List<Trabajo> RealizadosProveedor(String idProveedor) {

        return trabajoRepositorio.RealizadosProveedor(idProveedor);
    }

    @Transactional//(readOnly=True)

    public List<Trabajo> TodosProveedor(String idProveedor) {

        return trabajoRepositorio.TodosProveedor(idProveedor);
    }

    //FUNCIONALIDADES
    @Transactional
    public void Calificar(String id, Double calificacion,String comentario) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id del trabajo está vacío");
        }

        Optional<Trabajo> t = trabajoRepositorio.findById(id);
        if (t.isPresent()) {
            Trabajo trabajo = t.get();
            trabajo.setCalificacion(calificacion);

            if (comentario != null ) {//&& !comentario.isEmpty()
                trabajo.setComentario(comentario);
            }

            // Guardar los cambios en la base de datos
            trabajoRepositorio.save(trabajo);
        } else {
            throw new MyException("El trabajo con el id especificado no existe");
        }
    }

    @Transactional
    public void MarcarComoRealizado(String id) throws MyException {
        Optional<Trabajo> t = trabajoRepositorio.findById(id);
        if (id == null || id.isEmpty()) {
            throw new MyException("Falta el id del trabajo");
        }
        if (t.isPresent()) {
            Trabajo trabajo = t.get();
            trabajo.setRealizado(true);
        }
    }

    @Transactional
    public void ResponderSolicitud(String id, String accion) throws MyException {
        Optional<Trabajo> t = trabajoRepositorio.findById(id);
        if (id == null || id.isEmpty()) {
            throw new MyException("Falta el id del trabajo");
        }

        //accion Aceptada o Rechazada
        if (t.isPresent()) {
            Trabajo trabajo = t.get();
            trabajo.setSolicitud(accion);
        }
    }

    @Transactional
    public void ComentarioInapropiado(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Trabajo trabajo = respuesta.get();
            trabajo.setComentario("Sin comentario");

        }
    }

    @Transactional
    public void AceptarSolicitud(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Trabajo trabajo = respuesta.get();
            trabajo.setSolicitud("ACEPTADA");

        }
    }

    @Transactional
    public void RechazarSolicitud(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Trabajo trabajo = respuesta.get();
            trabajo.setSolicitud("RECHAZADA");

        }
    }

    @Transactional
    public void CancelarSolicitud(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        //un usuario puede cancelar la solicitud si se arrepiente
        if (respuesta.isPresent()) {

            Trabajo trabajo = respuesta.get();
            trabajo.setSolicitud("RECHAZADA POR USUARIO");

        }
    }

    //VALIDACIONES
    private void validar(String IdUsuario, String IdProveedor, String descripcion) throws MyException {

        if (IdUsuario == null || IdUsuario.isEmpty()) {
            throw new MyException("Debe ingresar el id del usuario");
        }

        if (IdProveedor == null || IdProveedor.isEmpty()) {
            throw new MyException("Debe ingresar el id del proveedor");
        }

        if (descripcion == null || descripcion.isEmpty()) {
            throw new MyException("Debe ingresar una descripción");
        }

    }

    private void validarTrabajo(String idTrabajo, String IdProveedor, String IdUsuario, String descripcion, String comentario) throws MyException {

        if (idTrabajo == null || idTrabajo.isEmpty()) {
            throw new MyException("Debe ingresar un comentario");
        }
        if (IdProveedor == null || IdProveedor.isEmpty()) {
            throw new MyException("Debe ingresar el id del proveedor");
        }
        if (IdUsuario == null || IdUsuario.isEmpty()) {
            throw new MyException("Debe ingresar el id del usuario");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new MyException("Debe ingresar una descripción");
        }
        if (comentario == null || comentario.isEmpty()) {
            throw new MyException("Debe ingresar un comentario");
        }
    }

}
