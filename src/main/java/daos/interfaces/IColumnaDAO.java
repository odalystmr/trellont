package daos.interfaces;


import java.util.List;

import domain.Columna;
import domain.Proyecto;
import exceptions.DAOException;

public interface IColumnaDAO extends ErroresBD {
	
	public void insertarColumna(Columna columna)throws DAOException;
	public int  borrarColumna(Columna columna)throws DAOException;
	public int  borrarColumnaById(int id)throws DAOException;
	public int  modificarColumna(Columna columna)throws DAOException;
	public Columna recuperarColumna(Columna columna)throws DAOException;
	public Columna  recuperarColumnaById(int id)throws DAOException;
	public List<Columna>  recuperarTodosColumna(Proyecto proyecto)throws DAOException;
	
}
