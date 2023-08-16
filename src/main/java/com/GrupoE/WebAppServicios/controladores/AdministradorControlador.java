package com.GrupoE.WebAppServicios.controladores;

import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import com.GrupoE.WebAppServicios.errores.MyException;
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
@RequestMapping("/administrador")
public class AdministradorControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PortalControlador portalControlador;

    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo, HttpSession session) {
        // Cargamos los datos del usuario o proveedor logueado directamente aquí
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        // Si el usuario logueado no es un administrador, se redirige a la página de inicio
        Usuario logueadoUsuario = (Usuario) session.getAttribute("usuarioSession");
        if (logueadoUsuario != null && !logueadoUsuario.getRol().equals(Rol.ADMIN)) {
            return "redirect:/inicio"; // Redireccionamos a la página de inicio o a donde corresponda
        }
        // Si llegamos hasta aquí, el usuario logueado es un administrador y puede acceder a la lista de usuarios
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_lista.html";
    }

    @GetMapping("/modificarEstado/{id}")
    public String modificarEstado(@PathVariable String id) {
        usuarioServicio.cambiarEstado(id);
        // Agregamos un parámetro para evitar el caché y asegurarnos de obtener la lista actualizada
        return "redirect:/administrador/usuarios?cache=false";

    }

    @GetMapping("/modificarRol/{id}")
    public String modificarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);
        // Agregamos un parámetro para evitar el caché y asegurarnos de obtener la lista actualizada
        return "redirect:/administrador/usuarios?cache=false";

    }

}
