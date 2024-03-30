package com.GrupoE.WebAppServicios.servicios;

import com.GrupoE.WebAppServicios.entidades.Imagen;
import com.GrupoE.WebAppServicios.entidades.Proveedor;
import com.GrupoE.WebAppServicios.entidades.Trabajo;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.repositorios.ProveedorRepositorio;
import com.GrupoE.WebAppServicios.repositorios.TrabajoRepositorio;
import com.GrupoE.WebAppServicios.repositorios.UsuarioRepositorio;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    
    
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional

    public void registrar(MultipartFile archivo, String nombre, String apellido, String direccion, String servicio, String remuneracion, String descripcion, String email, String password, String password2)throws MyException {

        validar(nombre, apellido, direccion, descripcion,  email, password, password2);
//remuneracion,
        Proveedor proveedor = new Proveedor();
        proveedor.setCantTrabajos(0.0);
        proveedor.setCalificacion(0.0);
        proveedor.setNombre(nombre);

        proveedor.setApellido(apellido);
        proveedor.setDireccion(direccion);
        proveedor.setServicio(servicio);

        proveedor.setDescripcion(descripcion);
        proveedor.setRemuneracion(remuneracion);

        proveedor.setEmail(email);
        
        

        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

        proveedor.setRol(Rol.PROVEEDOR);
                
       
        Imagen imagen = imagenServicio.guardar(archivo);
        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void VolverseProveedor(Imagen imagen, String nombre, String apellido, String direccion, String servicio, String remuneracion, String descripcion, String email, String password, String password2) throws MyException {

        validar(nombre, apellido, direccion, descripcion,  email, password, password2);
//remuneracion,
        Proveedor proveedor = new Proveedor();
        proveedor.setCantTrabajos(0.0);
        proveedor.setCalificacion(0.0);
        proveedor.setNombre(nombre);

        proveedor.setApellido(apellido);
        proveedor.setDireccion(direccion);
        proveedor.setServicio(servicio);

        proveedor.setDescripcion(descripcion);
        proveedor.setRemuneracion(remuneracion);

        proveedor.setEmail(email);

        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

        proveedor.setRol(Rol.PROVEEDOR);

        proveedor.setImagen(imagen);
        System.out.println("apunto de guardar el prov nuevp");
        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idProveedor, String idTrabajo, Integer cantTrabajos, String nombre, String apellido, String direccion, String descripcion, String remuneracion, String email, String password, String password2) throws MyException {
        Double calificacion = 0.0;
        validar(nombre, apellido, direccion, descripcion,  email, password, password2);
//remuneracion,
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);
        Optional<Trabajo> respuestaTrabajo = trabajoRepositorio.findById(idTrabajo);
        if (respuestaTrabajo.isPresent()) {
            Trabajo trabajo = respuestaTrabajo.get();
            calificacion = trabajo.getCalificacion();
            cantTrabajos = cantTrabajos + 1;
        }
        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setCalificacion((calificacion + proveedor.getCalificacion()) / cantTrabajos);
            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setApellido(apellido);
            proveedor.setDireccion(direccion);

            proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

            proveedor.setRol(Rol.PROVEEDOR);

            String idImagen = null;

            if (proveedor.getImagen() != null) { //si hay imagen para cargar , la cargamos
                idImagen = proveedor.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            proveedor.setImagen(imagen);

            proveedorRepositorio.save(proveedor);
        }
    }

    @Transactional
    public void actualizarProveedor(HttpSession session, MultipartFile archivo, String idProveedor,
            String nombre, String apellido, String direccion,
            String descripcion, String remuneracion, String email,
            String password, String password2) throws MyException {

        validarActualizacion(session, nombre, apellido, direccion, descripcion,  email, password, password2);
//remuneracion,
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(password, proveedor.getPassword())) {
                throw new MyException("Has ingresado una contraseña errónea");
            }

            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setApellido(apellido);
            proveedor.setDireccion(direccion);
            proveedor.setDescripcion(descripcion);
            proveedor.setRemuneracion(remuneracion);

            String idImagen = null;

            if (proveedor.getImagen() != null) { //si hay imagen para cargar , la cargamos
                idImagen = proveedor.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            proveedor.setImagen(imagen);
            proveedorRepositorio.save(proveedor);
        }
    }

    @Transactional
    public void calificarProveedor(String trabajoId,Double calificacion) throws MyException {
        
        //validarActualizacion(session,nombre, apellido, direccion, descripcion, remuneracion, email, password, password2);
        Double calific=0.0;
        
        Double cant=0.0;
        Double calificProm=0.0;
        Trabajo trabajo = trabajoRepositorio.TrabajoRealizadosProveedor(trabajoId);
        
            Optional<Proveedor> respuesta = proveedorRepositorio.findById(trabajo.getProveedor().getId());
            
        if (respuesta.isPresent()) {
            
            Proveedor proveedor = respuesta.get();
            List<Trabajo>trabajosProveedor=trabajoRepositorio.RealizadosProveedor(proveedor.getId());
            for(Trabajo trabajoProv : trabajosProveedor){
                calific=trabajoProv.getCalificacion()+ calific;
                cant = cant +1;
            }
            //cant=trabajosProveedor.size();
            proveedor.setCantTrabajos(cant);
            //Integer calif=proveedor.getCalificacion();
            calificProm=((double)Math.round((calific/cant)*100d)/100d);
            //calificProm=calific/cant;
            
            //calific=cant;
            proveedor.setCalificacion(calificProm);///(cant+1)
            
            proveedorRepositorio.save(proveedor);
        }
    }
    
    public Proveedor getOne(String id) {
        return proveedorRepositorio.getOne(id);
    }

    @Transactional//(readOnly=True)
    public List<Proveedor> listarProveedores() {

        List<Proveedor> proveedores = proveedorRepositorio.findAll();

        

        return proveedores;
    }

    @Transactional//(readOnly=True)
    public List<Proveedor> listarProveedoresPorDescripcion(String servicio) {

        List<Proveedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.buscarPorNombreDescripcion(servicio);

        return proveedores;
    }

    @Transactional
    public void eliminar(String id) throws MyException {
        Proveedor proveedor = proveedorRepositorio.getById(id);
        proveedorRepositorio.delete(proveedor);
    }

//VALIDACIONES
    private void validar(String nombre,String apellido, String direccion, String descripcion,  String email, String password, String password2) throws MyException {
             //String remuneracion, 
        /*Validar Nombre*/
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre no pude ser nulo ni estar vacio");
        }
        String nombrevalidar = nombre.toUpperCase();
        for (int i = 0; i < nombrevalidar.length(); i++) {
            char letra = nombrevalidar.charAt(i);
            if (letra == 32) {
                continue;
            }
            if ((letra < 65 || letra > 90) && (letra != 209)) {
                throw new MyException("El nombre contiene algo que no sea una letra");
            }
        }
        /*Validar Apellido*/
        if (apellido == null || apellido.isEmpty()) {
            throw new MyException("El apellido no puede ser nulo o estar vacío");
        }
        String apellidovalidar = apellido.toUpperCase();
        for (int i = 0; i < apellidovalidar.length(); i++) {
            char letra = apellidovalidar.charAt(i);
            if (letra == 32) {
                continue;
            }
            if ((letra < 65 || letra > 90) && (letra != 209)) {
                throw new MyException("El apellido contiene algo que no sea una letra");
            }
        }
        /*Validar Descripcion*/
        if (descripcion == null || descripcion.isEmpty()) {
            throw new MyException("La descripcion no puede ser nula o estar vacío");
        }
        ///*Validar Remuneracion*/
        //if (remuneracion == null || remuneracion.isEmpty()) {
        //    throw new MyException("La remuneracion no puede ser nula o estar vacío");
        //}
        //boolean val = false;
        //try {
        //    double valor = Double.parseDouble(remuneracion);
        //} catch (Exception e) {
        //    val = true;
        //}
        //if (val) {
        //    throw new MyException("La remuneracion contiene algo que no sea un numero");
        //}
        /*Validar Direccion*/
        if (direccion == null || direccion.isEmpty()) {
            throw new MyException("La direccion no puede ser nula o estar vacío");
        }
        /*Validar Email*/
        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacío");
        }
        //  if (proveedorRepositorio.buscarProveedorPorEmail(email) != null || usuarioRepositorio.buscarUsuarioPorEmail(email) != null) {
          //  throw new MyException("Hay otro usuario con este email. Elija otro");
        //}  
         
        /*Validar contraseña*/
        if (usuarioRepositorio.buscarUsuarioPorEmail(email) != null) {
            throw new MyException("El email ya se encuentra registrado");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarProveedorPorEmail(email);
        if (proveedor != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());//concatenacion ROLE_USER

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("proveedorSession", proveedor);

            //ServletRequestAttributes attrP = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            //HttpSession session = attrP.getRequest().getSession(true);
            //session.setAttribute("proveedorSession", proveedor);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }
    }

    public void validarActualizacion(HttpSession session, String nombre, String apellido, String direccion, String descripcion,  String email, String password, String password2) throws MyException {
//String remuneracion,
        /*Validar Nombre*/
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre no pude ser nulo ni estar vacio");
        }
        String nombrevalidar = nombre.toUpperCase();
        for (int i = 0; i < nombrevalidar.length(); i++) {
            char letra = nombrevalidar.charAt(i);
            if (letra == 32) {
                continue;
            }
            if ((letra < 65 || letra > 90) && (letra != 209)) {
                throw new MyException("El nombre contiene algo que no sea una letra");
            }
        }
        /*Validar Apellido*/
        if (apellido == null || apellido.isEmpty()) {
            throw new MyException("El apellido no puede ser nulo o estar vacío");
        }
        String apellidovalidar = apellido.toUpperCase();
        for (int i = 0; i < apellidovalidar.length(); i++) {
            char letra = apellidovalidar.charAt(i);
            if (letra == 32) {
                continue;
            }
            if ((letra < 65 || letra > 90) && (letra != 209)) {
                throw new MyException("El apellido contiene algo que no sea una letra");
            }
        }
        /*Validar Descripcion*/
        if (descripcion == null || descripcion.isEmpty()) {
            throw new MyException("La descripcion no puede ser nula o estar vacío");
        }
        /*Validar Remuneracion
        if (remuneracion == null || remuneracion.isEmpty()) {
            throw new MyException("La remuneracion no puede ser nula o estar vacío");
        }
        boolean val = false;
        try {
            double valor = Double.parseDouble(remuneracion);
        } catch (Exception e) {
            val = true;
        }
        if (val) {
            throw new MyException("La remuneracion contiene algo que no sea un numero");
        }*/
        /*Validar Direccion*/
        if (direccion == null || direccion.isEmpty()) {
            throw new MyException("La direccion no puede ser nula o estar vacío");
        }
        /*Validar Email*/
        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacío");
        }

        Proveedor proveedorLogueado = (Proveedor) session.getAttribute("proveedorSession");
        Proveedor respuesta = proveedorRepositorio.buscarProveedorPorEmail(email);
        if (respuesta != null && respuesta.getId() != null && proveedorLogueado != null && proveedorLogueado.getId() != null) {
            if (!respuesta.getId().equals(proveedorLogueado.getId())) {
                throw new MyException("El email ya se encuentra registrado por otro usuario");
            }
        }
        /*Validar contraseña*/
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

}
