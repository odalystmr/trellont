package daos.interfaces;


import java.util.List;

import domain.Proyecto;
import domain.Usuario;
import exceptions.DAOException;

public interface IProyectoDAO extends ErroresBD {
	
	public void insertarProyecto(Proyecto proyecto)throws DAOException;
	public int  borrarProyecto(Proyecto proyecto)throws DAOException;
	public int  borrarProyectoById(int id)throws DAOException;
	public int  modificarProyecto(Proyecto proyecto)throws DAOException;
	public Proyecto recuperarProyecto(Proyecto proyecto)throws DAOException;
	public Proyecto  recuperarProyectoById(int id)throws DAOException;
	public List<Proyecto>  recuperarTodosProyecto()throws DAOException;
	public List<Proyecto> recuperarTodosProyectoDeUsuario(Usuario usuario)throws DAOException;
	
}
