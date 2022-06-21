
package servicios;

import java.util.ArrayList;
import java.util.List;

import domain.Columna;
import domain.Comentario;
import domain.Tarea;
import daos.ComentarioDAO;
import daos.TareaDAO;

import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;

public class ServicioTarea {

	public ServicioTarea() {
		// TODO Auto-generated constructor stub
	}
	public void insertarTarea(Tarea tarea) throws ServiceException{
		TransaccionesManager trans = null;
		try {

			//tiene sesion iniciada

			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			tareaDAO.insertarTarea(tarea);

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
	public int modificarTarea(Tarea tarea) throws ServiceException{
		TransaccionesManager trans = null;
		int modificar=0;
		try {			
			//tiene sesion iniciada

			//este tarea pertenece al usuario? 

			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			modificar = tareaDAO.modificarTarea(tarea);

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
	public int borrarTarea(Tarea tarea) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			ServicioComentario servicioComentario= new ServicioComentario();

			List <Comentario> listaComentario = servicioComentario.recuperarTodosComentario(tarea);
			for(int i = 0; i< listaComentario.size(); i++) {
				servicioComentario.borrarComentario(listaComentario.get(i));
			}
			borrado = tareaDAO.borrarTarea(tarea);

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
	public int borrarTareaById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			borrado = tareaDAO.borrarTareaById(id);

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
	public Tarea recuperarTarea (Tarea tarea) throws ServiceException{
		TransaccionesManager trans = null;

		try {
			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			tarea = tareaDAO.recuperarTarea(tarea);

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
		return tarea;
	}
	public Tarea recuperarTareaById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		Tarea tarea=null;
		try {
			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			tarea = tareaDAO.recuperarTareaById(id);

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
		return tarea;
	}
	public List<Tarea> recuperarTodosTarea (Columna columna) throws ServiceException{
		TransaccionesManager trans = null;
		List<Tarea> list = new ArrayList<Tarea>();
		try {
			trans = new TransaccionesManager();
			TareaDAO tareaDAO = trans.getTareaDAO();
			list = tareaDAO.recuperarTodosTarea(columna);

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
