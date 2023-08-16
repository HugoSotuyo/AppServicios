package com.GrupoE.WebAppServicios.servicios;

import com.GrupoE.WebAppServicios.entidades.Imagen;
import com.GrupoE.WebAppServicios.entidades.Usuario;
import com.GrupoE.WebAppServicios.enumeraciones.Rol;
import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.repositorios.UsuarioRepositorio;
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
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String barrio, String direccion, String email, String password, String password2) throws MyException {

        validar(nombre, apellido, barrio, direccion, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);

        usuario.setApellido(apellido);

        usuario.setBarrio(barrio);

        usuario.setDireccion(direccion);

        usuario.setEmail(email);

        usuario.setActivo(true);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);

        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(HttpSession session, MultipartFile archivo, String idUsuario, String nombre, String apellido, String barrio, String direccion, String email, String password, String password2) throws MyException {

        validarActualizacion(session, nombre, apellido, barrio, direccion, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                throw new MyException("Has ingresado una contraseña errónea");
            }

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setBarrio(barrio);
            usuario.setDireccion(direccion);
            usuario.setEmail(email);
            String idImagen = null;

            if (usuario.getImagen() != null) { //si hay imagen para cargar , la cargamos
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }
    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional//(readOnly=True)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void eliminar(String id) throws MyException {
        Usuario usuario = usuarioRepositorio.getById(id);
        usuarioRepositorio.delete(usuario);
    }

    @Transactional
    public List<Usuario> buscarPorApelido(String apellido) throws MyException {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.buscarPorApellidoUsuarios(apellido);
        return usuarios;
    }

    private void validar(String nombre, String apellido, String barrio, String direccion, String email, String password, String password2) throws MyException {

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
        /*Validar Barrio*/
        if (barrio == null || barrio.isEmpty()) {
            throw new MyException("Debe seleccionar un barrio");
        }
        /*Validar Direccion*/
        if (direccion == null || direccion.isEmpty()) {
            throw new MyException("La direccion no puede ser nulo o estar vacío");
        }
        /*Validar email*/
        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacío");
        }

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

    private void validarActualizacion(HttpSession session, String nombre, String apellido,
            String barrio, String direccion, String email, String password, String password2) throws MyException {

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
        /*Validar Barrio*/
        if (barrio == null || barrio.isEmpty()) {
            throw new MyException("Debe seleccionar un barrio");
        }
        /*Validar Direccion*/
        if (direccion == null || direccion.isEmpty()) {
            throw new MyException("La direccion no puede ser nulo o estar vacío");
        }
        /*Validar email*/
        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacío");
        }

        Usuario logueadoUsuario = (Usuario) session.getAttribute("usuarioSession");
        Usuario respuesta = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (respuesta != null && !respuesta.getId().equals(logueadoUsuario.getId())) {
            /*Se comprueba si se encontró un usuario y si su ID no es igual al ID del usuario logueado. 
            Si ambas condiciones son verdaderas, se lanza una excepción */
            throw new MyException("El email ya se encuentra registrado por otro usuario");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Transactional
    public void crearPrimerUsuarioAdmin() throws MyException {
        Usuario adminUsuario = usuarioRepositorio.buscarUsuarioPorEmail("admin@example.com");
        if (adminUsuario == null) {
            // El usuario no existe, crea el primer usuario admin
            adminUsuario = new Usuario();
            adminUsuario.setNombre("Nombre del Admin");
            adminUsuario.setApellido("Apellido del Admin");
            adminUsuario.setBarrio("Barrio del Admin");
            adminUsuario.setDireccion("Dirección del Admin");
            adminUsuario.setEmail("admin@example.com");
            adminUsuario.setPassword(new BCryptPasswordEncoder().encode("contraseña"));
            adminUsuario.setRol(Rol.ADMIN);


            /*try {
                // Cargar el archivo del logo desde la ubicación
                File file = new File("src/main/resources/static/logo/Logo_FINAL.png");
                byte[] fileContent = Files.readAllBytes(file.toPath());

                // Crear un recurso a partir del contenido del archivo de imagen
                ByteArrayResource resource = new ByteArrayResource(fileContent);

                // Crear un objeto MultipartFile utilizando el recurso creado
                MultipartFile archivoLogo = new org.springframework.mock.web.MockMultipartFile(
                        "Logo_FINAL.png",
                        "Logo_FINAL.png",
                        "image/png",
                        resource.getInputStream()
                );

                // Guardar el logo como una entidad Imagen
                Imagen imagenLogo = imagenServicio.guardar(archivoLogo);
                adminUsuario.setImagen(imagenLogo);
            } catch (IOException e) {
                // Manejar cualquier error que pueda ocurrir al cargar el logo
                System.err.println("Error al cargar el logo: " + e.getMessage());
            }*/
            usuarioRepositorio.save(adminUsuario);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuarioSession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }
    }

    @Transactional
    public void cambiarEstado(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuario.getActivo() == true) {

                usuario.setActivo(false);

            } else if (usuario.getActivo() == false) {
                usuario.setActivo(true);
            }
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuario.getRol() == Rol.ADMIN) {

                usuario.setRol(Rol.USER);

            } else if (usuario.getRol() == Rol.USER) {
                usuario.setRol(Rol.ADMIN);
            }
        }
    }

    @Transactional
    public void CambiarAProveedor(String descripcion, String remuneracion,
            String password, String password2, String usuarioId, String servicio) throws MyException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(usuarioId);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            String nombre = usuario.getNombre();
            String apellido = usuario.getApellido();
            String email = usuario.getEmail();
            String direccion = usuario.getDireccion();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                throw new MyException("Has ingresado una contraseña errónea");
            }
            Imagen archivo = null;
            Imagen imagenUsuario = usuario.getImagen();
            if (imagenUsuario != null) {
                archivo = usuario.getImagen();
            }
            usuario.setRol(Rol.PROVEEDOR);
            usuario.setEmail("usuario@deshabilitado.com");
            usuario.setActivo(false);
            System.out.println("datos usaurio cambiados");
            proveedorServicio.VolverseProveedor(archivo, nombre, apellido, direccion, servicio, remuneracion, descripcion, email, password, password2);

        }
    }

}
