
package com.GrupoE.WebAppServicios.componentes;

import com.GrupoE.WebAppServicios.errores.MyException;
import com.GrupoE.WebAppServicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrimerUsuarioAdmin {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public PrimerUsuarioAdmin(UsuarioServicio usuarioServicio) throws MyException{
        this.usuarioServicio = usuarioServicio;
        crearPrimerUsuarioAdmin();
    }

    private void crearPrimerUsuarioAdmin() throws MyException{
        usuarioServicio.crearPrimerUsuarioAdmin();
    }
}
