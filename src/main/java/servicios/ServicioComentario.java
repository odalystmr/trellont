package servicios;

import java.util.ArrayList;
import java.util.List;

import domain.Comentario;
import domain.Tarea;
import daos.ComentarioDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;

public class ServicioComentario {

	public ServicioComentario() {
		// TODO Auto-generated constructor stub
	}
	public void insertarComentario(Comentario comentario) throws ServiceException{
		TransaccionesManager trans = null;
		try {

			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			comentarioDAO.insertarComentario(comentario);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
	}
	public int modificarComentario(Comentario comentario) throws ServiceException{
		TransaccionesManager trans = null;
		 int modificar=0;
		try {

			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			modificar=comentarioDAO.modificarComentario(comentario);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return modificar;
	}
	public int borrarComentario(Comentario comentario) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			borrado = comentarioDAO.borrarComentario(comentario);

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!=null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return borrado;
	}	
	public int borrarComentarioById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			borrado = comentarioDAO.borrarComentarioById(id);

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!=null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return borrado;
	}
	public Comentario recuperarComentario(Comentario comentario) throws ServiceException{
		TransaccionesManager trans = null;

		try {
			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			comentario = comentarioDAO.recuperarComentario(comentario);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return comentario;
	}
	public Comentario recuperarComentarioById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		Comentario comentario=null;
		try {
			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			comentario = comentarioDAO.recuperarComentarioById(id);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return comentario;
	}
	public List<Comentario> recuperarTodosComentario(Tarea tarea) throws ServiceException{
		TransaccionesManager trans = null;
		List<Comentario> list = new ArrayList<Comentario>();
		try {
			trans = new TransaccionesManager();
			ComentarioDAO comentarioDAO = trans.getComentarioDAO();
			list = comentarioDAO.recuperarTodosComentario(tarea);


			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null)
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lógico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return list;
	}
}
