package com.GrupoE.WebAppServicios.controladores;

import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.entidades.Trabajo;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.servicios.ProveedorServicio;
import com.GrupoE.WebAppServicios.servicios.TrabajoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @author Camila Astrada 
@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @Autowired
    private TrabajoServicio trabajoServicio;
    @Autowired
    private PortalControlador portalControlador;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/registrarTrabajo/{id}")
    public String registrarTrabajo(@PathVariable String id, HttpSession session, ModelMap modelo, ModelMap modeloUsuario) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        // Cargamos los datos del usuario o proveedor logueado directamente aquí
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        modeloUsuario.put("logueado", logueado);
        modelo.put("proveedor", proveedorServicio.getOne(id));
        List<Trabajo> trabajosTodos = trabajoServicio.RealizadosProveedor(id);
        modelo.addAttribute("trabajosTodos", trabajosTodos);
        return "proveedor.html";
    }

    @GetMapping("/listarTrabajos/{id}")
    public String listarTrabajos(@PathVariable String id, HttpSession session, ModelMap modelo, ModelMap modeloUsuario) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        // Cargamos los datos del usuario o proveedor logueado directamente aquí
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        modeloUsuario.put("logueado", logueado);
        modelo.put("proveedor", proveedorServicio.getOne(id));
        List<Trabajo> trabajos = trabajoServicio.TodosProveedor(id);
        modelo.addAttribute("trabajos", trabajos);
        return "ListaTrabajosRealizados.html";
    }

    /*
    @GetMapping("/registrarTrabajo")
    public String registroTrabajo(@RequestParam(name = "id", required = false, defaultValue = "0") String id,
            ModelMap modelo, HttpSession session, ModelMap modeloUsuario) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modeloUsuario.put("logueado", logueado);
        modelo.put("proveedor", proveedorServicio.getOne(id));
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        return "registroTrabajo.html";
    }
     */
 /*
    @GetMapping("vista/{id}")
    public String vista(@PathVariable String id, ModelMap modelo){
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "vista_noticia.html";
    }
     */
    @PostMapping("/registroTrabajo")
    public String registroTrabajo(@RequestParam String idLogueado, @RequestParam String idProveedor, @RequestParam(required = false, defaultValue = " Sin descripcion") String descripcion,
            ModelMap modelo, HttpSession session) throws MyException {

        try {
            trabajoServicio.registrar(idLogueado, idProveedor, descripcion);
            modelo.put("exito", "Trabajo registrado correctamente");

            return "inicio.html";
        } catch (MyException ex) {
            modelo.put("Error", ex.getMessage());
            return "registroTrabajo.html";
        }
    }

    @GetMapping("/listaTrabajo/allTrabajos")

    public String listarAllTrabajos(ModelMap modelo, String idProveedor, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        List<Trabajo> trabajos = trabajoServicio.listarTrabajos();  //lista todos los trabajos
        modelo.addAttribute("trabajos", trabajos);

        //lista los trabajos no realizados de un proveedor en particular
        return "trabajo_lista.html";

    }

//FUNCIONALIDADES
// RestringirComentario
    @GetMapping("/borrarComentario/{id}")
    public String ComentarioInapropiado(@PathVariable String id) {
        trabajoServicio.ComentarioInapropiado(id);

        return "redirect:/trabajo/listaTrabajo/allTrabajos?cache=false";

    }

    @PostMapping("/aceptarSolicitud/{id}")
    public String Aceptar(@PathVariable String id) {
        trabajoServicio.AceptarSolicitud(id);

        return "redirect:/perfilProveedor/{id}?cache=false";

    }

    @PostMapping("/rechazarSolicitud/{id}")
    public String Rechazar(@PathVariable String id) {
        trabajoServicio.RechazarSolicitud(id);

        return "redirect:/perfilProveedor/{id}?cache=false";

    }

    @PostMapping("/marcarRealizado/{id}")
    public String MarcarRealizado(@PathVariable String id) throws MyException {
        trabajoServicio.MarcarComoRealizado(id);

        return "redirect:/perfilProveedor/{id}?cache=false";

    }

    @PostMapping("/cancelarSolicitud/{id}")
    public String CancelarSolicitud(@PathVariable String id) {
        trabajoServicio.CancelarSolicitud(id);

        return "redirect:/perfilUser/{id}?cache=false";

    }

    @PostMapping("/calificar")
    public String calificarTrabajo(@RequestParam("trabajoId")String trabajoId,
            @RequestParam("comentario")String comentario,@RequestParam("proveedorId") String proveedorId,
            @RequestParam("calificacion") Double calificacion,
            @RequestParam("usuarioId")String usuarioId) throws MyException {

        // Llama al servicio 'trabajoServicio' para calificar el trabajo
        trabajoServicio.Calificar(trabajoId,  calificacion,comentario);
        proveedorServicio.calificarProveedor(trabajoId, calificacion);
        // Redirecciona a la página de perfil del usuario después de calificar
        String redirectUrl = "/perfilUser/" + usuarioId + "?cache=false";
        return "redirect:" + redirectUrl;
    }
}
