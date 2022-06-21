package daos.interfaces;


import java.util.List;

import domain.Usuario;
import exceptions.DAOException;

public interface IUsuarioDAO extends ErroresBD {
	
	public void insertarUsuario(Usuario usuario)throws DAOException;
	public int  borrarUsuario(Usuario usuario)throws DAOException;
	public int  borrarUsuarioById(int id)throws DAOException;
	public int  modificarUsuario(Usuario usuario)throws DAOException;
	public Usuario recuperarUsuario(Usuario usuario)throws DAOException;
	public Usuario  recuperarUsuarioById(int id)throws DAOException;
	public Usuario  recuperarUsuarioByEmail(String email)throws DAOException;
	public List<Usuario>  recuperarTodosUsuario()throws DAOException;
	
}
