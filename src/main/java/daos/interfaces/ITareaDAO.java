package daos.interfaces;


import java.util.List;

import domain.Columna;
import domain.Tarea;
import exceptions.DAOException;

public interface ITareaDAO extends ErroresBD {
	
	public void insertarTarea(Tarea tarea)throws DAOException;
	public int  borrarTarea(Tarea tarea)throws DAOException;
	public int  borrarTareaById(int id)throws DAOException;
	public int  modificarTarea(Tarea tarea)throws DAOException;
	public Tarea recuperarTarea(Tarea tarea)throws DAOException;
	public Tarea  recuperarTareaById(int id)throws DAOException;
	public List<Tarea> recuperarTodosTarea(Columna columna)throws DAOException;
	
}
