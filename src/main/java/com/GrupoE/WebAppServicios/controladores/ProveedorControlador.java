package com.GrupoE.WebAppServicios.controladores;

import com.GrupoE.WebAppServicios.entidades.Proveedor;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private PortalControlador portalControlador;
    @Autowired
    private TrabajoServicio trabajoServicio;

    @GetMapping("lista/fontanero")
    public String listarFontanero(ModelMap modelo, HttpSession session) {
        // Llamamos al método logueado del controlador base para cargar los datos del usuario o proveedor logueado
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "fontanero";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/electricista")
    public String listarElectricista(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion;
        descripcion = "electricista";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/jardinero")
    public String listarJardinero(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "jardineria";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/limpieza")
    public String listarLimpieza(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "limpieza";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/pintura")
    public String listarPintura(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "pintura";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/carpinteria")
    public String listarCarpinteria(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "carpinteria";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/albañileria")
    public String listarAlbañileria(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "albañileria";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/cerrajeria")
    public String listarCerrajeria(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "cerrajeria";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/climatizacion")
    public String listarClimatizacion(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "climatizacion";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/electronica")
    public String listarElectronica(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "electronica";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/seguridad")
    public String listarSeguridad(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "seguridad";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/mudanzas")
    public String listarMudanzas(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "mudanzas";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/mecanica")
    public String listarMecanica(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "mecanica";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/grafica")
    public String listarGrafica(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }

        String descripcion = "grafica";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }

    @GetMapping("lista/software")
    public String listarSoftware(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        String descripcion = "software";
        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorDescripcion(descripcion);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }
    
    @GetMapping("/listaCompleta")
    public String listar(ModelMap modelo, HttpSession session) {
        String redireccion = portalControlador.logueado(modelo, session);
        if (redireccion != null) {
            // Si el método logueado devuelve una redirección, la retornamos
            return redireccion;
        }
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_lista.html";
    }
    @PostMapping("/calificar/{id}")
    public String calificar(@PathVariable String id, @RequestParam String comentario, @RequestParam Double calificacion) throws MyException{
        trabajoServicio.Calificar(id,calificacion,comentario);;
        return "inicio.html";
    }

}
