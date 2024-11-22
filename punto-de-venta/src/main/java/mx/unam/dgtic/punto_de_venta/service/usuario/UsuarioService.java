package mx.unam.dgtic.punto_de_venta.service.usuario;

import mx.unam.dgtic.punto_de_venta.model.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    Page<Usuario> buscarUsuario(Pageable pageable);
    List<Usuario> buscarUsuario();
    void guardar(Usuario usuario);
    void borrar(Integer id);
    Usuario buscarUsuarioId(Integer id);
}
