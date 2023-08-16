package com.GrupoE.WebAppServicios.controladores;

import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.entidades.Trabajo;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.servicios.ProveedorServicio;
import com.GrupoE.WebAppServicios.servicios.TrabajoServicio;
import com.GrupoE.WebAppServicios.servicios.UsuarioServicio;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private TrabajoServicio trabajoServicio;

    @GetMapping("/")
    public String index(HttpSession session, ModelMap modelo) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        Usuario logueadoUsuario = (Usuario) session.getAttribute("usuarioSession");
        if (logueadoUsuario != null) {
            // El usuario está logueado, redirigimos a la página de inicio
            return "inicio.html";
        } else {
            // El usuario no está logueado, redirigimos a la página de inicio de sesión (index.html)
            return "index.html";
        }
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        return "registroUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String barrio, @RequestParam String direccion, @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2, ModelMap modelo, MultipartFile archivo, HttpSession session) throws MyException {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, barrio, direccion, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");
            return "index.html";
        } catch (MyException ex) {
            modelo.put("Error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registroUsuario.html";
        }
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/registroProveedor")
    public String registroProveedor() {
        return "registroProveedor.html";
    }

    @PostMapping("/registroProveedor")
    public String registroProveedor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String descripcion,
            @RequestParam String servicio, @RequestParam String remuneracion, @RequestParam String direccion, @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2, ModelMap modelo, MultipartFile archivo, HttpSession session) throws MyException {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        try {

            proveedorServicio.registrar(archivo, nombre, apellido, direccion, servicio, remuneracion, descripcion, email, password, password2);

            modelo.put("exito", "Proveedor registrado correctamente");
            return "index.html";
        } catch (MyException ex) {
            modelo.put("Error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registroProveedor.html";
        }
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/conocenos")
    public String nosotros(ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        return "nosotros.html";
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/perfilUser/{id}")
    public String perfilUser(@PathVariable String id, ModelMap modelo, HttpSession session) {

        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        //lista los trabajos que le faltan calificar al ususario logueado.
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioSession");
        if (usuarioLogueado != null) {

            Usuario usuario = usuarioLogueado;

            if (usuarioLogueado.getImagen() != null) {
                modelo.addAttribute("idImagen", usuarioLogueado.getImagen().getId());
            }

            modelo.addAttribute("usuario", usuario);
            List<Trabajo> RealizadosNoCalificados = trabajoServicio.trabajosRealizadosNoCalificados(usuarioLogueado.getId());
            modelo.addAttribute("RealizadosNoCalificados", RealizadosNoCalificados);

            List<Trabajo> TodosUsuario = trabajoServicio.TodosUsuario(usuarioLogueado.getId());
            modelo.addAttribute("TodosUsuario", TodosUsuario);
        }

        return "perfilUsuario.html";
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/perfilProveedor/{id}")
    public String perfilProveedor(@PathVariable String id, ModelMap modelo, HttpSession session) {

        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        Proveedor logueadoProveedor = (Proveedor) session.getAttribute("proveedorSession");
        if (logueadoProveedor != null) {
            Proveedor proveedor = logueadoProveedor;

            if (logueadoProveedor.getImagen() != null) {
                modelo.addAttribute("idImagen", logueadoProveedor.getImagen().getId());
            }

            modelo.addAttribute("proveedor", proveedor);

            List<Trabajo> NoRealizados = trabajoServicio.listarTrabajosNoRealizados(logueadoProveedor.getId());
            modelo.addAttribute("NoRealizados", NoRealizados);

            //lista todos los trabajos de un proveedor en particular
            List<Trabajo> trabajosProov = trabajoServicio.TodosProveedor(logueadoProveedor.getId());
            modelo.addAttribute("trabajosProov", trabajosProov);

            //lista los trabajos que no ha aceptado ni rechazado de un proveedor en particular
            List<Trabajo> solicitudes = trabajoServicio.Solicitudes(logueadoProveedor.getId());
            modelo.addAttribute("solicitudes", solicitudes);
        }

        return "perfilProveedor.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos!!");
        }

        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        // Llamamos al método logueado del controlador base para cargar los datos del usuario o proveedor logueado
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        return "inicio.html";
    }

    protected String logueado(ModelMap modelo, HttpSession session) {
        // Tu código para cargar los datos del usuario o proveedor logueado
        // Si es un usuario logueado
        Usuario logueadoUsuario = (Usuario) session.getAttribute("usuarioSession");
        if (logueadoUsuario != null) {

            if (logueadoUsuario.getRol().toString().equals("ADMIN")) {
                // No redirigimos aquí, simplemente retornamos null
                modelo.addAttribute("id", logueadoUsuario.getId());
                modelo.addAttribute("nombre", logueadoUsuario.getNombre());
                modelo.addAttribute("apellido", logueadoUsuario.getApellido());
                modelo.addAttribute("barrio", logueadoUsuario.getBarrio());
                modelo.addAttribute("direccion", logueadoUsuario.getDireccion());

                if (logueadoUsuario.getImagen() != null) {
                    modelo.addAttribute("idImagen", logueadoUsuario.getImagen().getId());
                }
                modelo.addAttribute("email", logueadoUsuario.getEmail());
                modelo.addAttribute("rol", logueadoUsuario.getRol().toString());
                return null;
            }

            modelo.addAttribute("id", logueadoUsuario.getId());
            modelo.addAttribute("nombre", logueadoUsuario.getNombre());
            modelo.addAttribute("apellido", logueadoUsuario.getApellido());
            modelo.addAttribute("barrio", logueadoUsuario.getBarrio());
            modelo.addAttribute("direccion", logueadoUsuario.getDireccion());
            modelo.addAttribute("email", logueadoUsuario.getEmail());
            modelo.addAttribute("rol", logueadoUsuario.getRol().toString());
        } // Si es un proveedor logueado
        else {
            Proveedor logueadoProveedor = (Proveedor) session.getAttribute("proveedorSession");
            if (logueadoProveedor != null) {
                modelo.addAttribute("id", logueadoProveedor.getId());
                modelo.addAttribute("nombre", logueadoProveedor.getNombre());
                modelo.addAttribute("apellido", logueadoProveedor.getApellido());
                modelo.addAttribute("direccion", logueadoProveedor.getDireccion());
                modelo.addAttribute("descripcion", logueadoProveedor.getDescripcion());
                modelo.addAttribute("remuneracion", logueadoProveedor.getRemuneracion());
                modelo.addAttribute("servicio", logueadoProveedor.getServicio());
                modelo.addAttribute("descripcion", logueadoProveedor.getDescripcion());
                modelo.addAttribute("cantTrabajos", logueadoProveedor.getCantTrabajos());
                modelo.addAttribute("descripcion", logueadoProveedor.getCalificacion());

                if (logueadoProveedor.getImagen() != null) {
                    modelo.addAttribute("idImagen", logueadoProveedor.getImagen().getId());
                }

                modelo.addAttribute("email", logueadoProveedor.getEmail());
                modelo.addAttribute("rol", logueadoProveedor.getRol().toString());
            } // Si no hay usuario ni proveedor logueado
        }

        return null; // Retornamos null si todo va bien (sin redirecciones)
    }

    @GetMapping("/actualizarUser")
    public String Actualizar(ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioSession");
        if (usuarioLogueado != null) {

            Usuario usuario = usuarioLogueado;

            modelo.addAttribute("usuario", usuario);
        }
        return "modificarUsuario.html";
    }

    @PostMapping("/actualizarUser")
    public String Actualizacion(@RequestParam("usuarioId") String usuarioId,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("barrio") String barrio,
            @RequestParam("direccion") String direccion,
            @RequestParam("email") String email,
            MultipartFile archivo,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            ModelMap modelo,
            HttpSession session) throws MyException {
        try {
            usuarioServicio.actualizar(session, archivo, usuarioId, nombre, apellido,
                    barrio, direccion, email, password, password2);
            Usuario usuarioActualizado = usuarioServicio.getOne(usuarioId);
            session.setAttribute("usuarioSession", usuarioActualizado);

            return "redirect:" + "/perfilUser/" + usuarioId + "?cache=false";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());

            return "modificarUsuario.html";
        }
    }

    @GetMapping("/perfil/actualizarProveedor")
    public String ActualizarProveedor(ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        Proveedor logueadoProveedor = (Proveedor) session.getAttribute("proveedorSession");

        if (logueadoProveedor != null) {
            modelo.addAttribute("proveedor", logueadoProveedor);

        }
        return "modificarProveedor.html";
    }

    @PostMapping("/actualizarProveedor")
    public String Actualizacion(@RequestParam("id") String id,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("direccion") String direccion,
            @RequestParam("email") String email,
            MultipartFile archivo,
            @RequestParam("remuneracion") String remuneracion,
            @RequestParam("servicio") String servicio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            ModelMap modelo,
            HttpSession session) throws MyException {
        try {
            proveedorServicio.actualizarProveedor(session, archivo, id, nombre, apellido, direccion, descripcion, remuneracion, email, password, password2);
            Proveedor proveedorActualizado = proveedorServicio.getOne(id);
            session.setAttribute("proveedorSession", proveedorActualizado);
            return "redirect:" + "/perfilProveedor/" + id + "?cache=false";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());

            return "modificarProveedor.html";
        }
    }

    @GetMapping("/serProveedor")
    public String SerProveedor(ModelMap modelo, HttpSession session) {
        String redireccion = logueado(modelo, session);
        if (redireccion != null){
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioSession");
        if (usuarioLogueado != null) {

            Usuario usuario = usuarioLogueado;
            modelo.addAttribute("usuario", usuario);
        }
        return "volverse_proveedor.html";
    }

    @PostMapping("/serProveedor")
    public String serProveedor(@RequestParam("usuarioId") String usuarioId,
            @RequestParam("remuneracion") String remuneracion,
            @RequestParam("servicio") String servicio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            ModelMap modelo,
            HttpSession session) throws MyException {
        try {
            usuarioServicio.CambiarAProveedor(descripcion, remuneracion, password, password2, usuarioId, servicio);

            return "redirect:" + "/";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "volverse_proveedor.html";
        }
    }
}
