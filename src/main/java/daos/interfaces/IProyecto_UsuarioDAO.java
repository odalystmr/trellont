package daos.interfaces;


import java.util.List;

import domain.Proyecto;
import domain.Proyecto_Usuario;
import exceptions.DAOException;

public interface IProyecto_UsuarioDAO extends ErroresBD {
	
	public void insertarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException;
	public int  borrarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException;
	public int  borrarProyecto_UsuarioById(int id, int id2)throws DAOException;
	public int  modificarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException;
	public Proyecto_Usuario recuperarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException;
	public Proyecto_Usuario  recuperarProyecto_UsuarioById(int id)throws DAOException;
	public List<Proyecto_Usuario>  recuperarTodosProyecto_Usuario(Proyecto proyecto)throws DAOException;
	
}
