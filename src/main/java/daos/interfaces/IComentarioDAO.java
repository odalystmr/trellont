package daos.interfaces;


import java.util.List;

import domain.Comentario;
import domain.Tarea;
import exceptions.DAOException;

public interface IComentarioDAO extends ErroresBD {
	
	public void insertarComentario(Comentario comentario)throws DAOException;
	public int  borrarComentario(Comentario comentario)throws DAOException;
	public int  borrarComentarioById(int id)throws DAOException;
	public int  modificarComentario(Comentario comentario)throws DAOException;
	public Comentario recuperarComentario(Comentario comentario)throws DAOException;
	public Comentario  recuperarComentarioById(int id)throws DAOException;
	public List<Comentario>  recuperarTodosComentario(Tarea tarea)throws DAOException;
	
}
