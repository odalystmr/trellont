package servicios;

import java.util.ArrayList;
import java.util.List;

import domain.Columna;
import domain.Proyecto;
import domain.Tarea;
import daos.ColumnaDAO;
import daos.TareaDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;

public class ServicioColumna {

	public ServicioColumna() {
		// TODO Auto-generated constructor stub
	}
	public void insertarColumna(Columna columna) throws ServiceException{
		TransaccionesManager trans = null;
		try {

			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			columnaDAO.insertarColumna(columna);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		
	}
	public int modificarColumna(Columna columna) throws ServiceException{
		TransaccionesManager trans = null;
		 int modificar=0;
		try {

			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			modificar=columnaDAO.modificarColumna(columna);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return modificar;
	}
	public int borrarColumna(Columna columna) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			ServicioTarea starea=new ServicioTarea();
			List <Tarea> listaTarea = starea.recuperarTodosTarea(columna);
			for(int i = 0; i< listaTarea.size(); i++) {
				starea.borrarTarea(listaTarea.get(i));
			}
					
			borrado = columnaDAO.borrarColumna(columna);

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!=null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return borrado;
	}	
	public int borrarColumnaById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			borrado = columnaDAO.borrarColumnaById(id);

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!=null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return borrado;
	}
	public Columna recuperarUsuario(Columna columna) throws ServiceException{
		TransaccionesManager trans = null;

		try {
			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			columna = columnaDAO.recuperarColumna(columna);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return columna;
	}
	public Columna recuperarColumnaById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		Columna columna=null;
		try {
			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			columna = columnaDAO.recuperarColumnaById(id);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return columna;
	}
	public List<Columna> recuperarTodosColumna(Proyecto proyecto) throws ServiceException{
		TransaccionesManager trans = null;
		List<Columna> list = new ArrayList<Columna>();
		try {
			trans = new TransaccionesManager();
			ColumnaDAO columnaDAO = trans.getColumnaDAO();
			list = columnaDAO.recuperarTodosColumna(proyecto);

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error L�gico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return list;
	}
}
