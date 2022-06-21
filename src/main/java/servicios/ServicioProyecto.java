package servicios;

import java.util.ArrayList;
import java.util.List;

import domain.Columna;
import domain.Proyecto;
import domain.Proyecto_Usuario;
import domain.Usuario;
import daos.ColumnaDAO;
import daos.ProyectoDAO;
import daos.Proyecto_UsuarioDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;

public class ServicioProyecto {

	public ServicioProyecto() {
		// TODO Auto-generated constructor stub
	}
	public void insertarProyecto(Proyecto proyecto) throws ServiceException{
		TransaccionesManager trans = null;
		try {
			
			//tiene sesion iniciada

			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			proyectoDAO.insertarProyecto(proyecto);

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

	public int modificarProyecto(Proyecto proyecto) throws ServiceException{
		TransaccionesManager trans = null;
		 int modificar=0;
		try {			
			//tiene sesion iniciada

			//este proyecto pertenece al usuario? 

			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			modificar=proyectoDAO.modificarProyecto(proyecto);

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

	public void relacionarParticipantes(Proyecto proyecto, List<Usuario> participantes) throws ServiceException{
		TransaccionesManager trans = null;

		try {			
			//tiene sesion iniciada

			//este proyecto pertenece al usuario? 

			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();

			for (int i = 0; i < participantes.size(); i++) {
				if (proyecto.getPropietario().getID() == participantes.get(i).getID()) {
					//Si el usuario de la lista es el propietario del proyecto no se inserta como participante
					continue;
				}

				proyectoDAO.relacionarParticipante(proyecto, participantes.get(i));
			}

			trans.closeCommit();
		} catch (DAOException e) {
			try{
				if(trans!= null) {
					trans.closeRollback();
				}
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

	public int borrarProyecto(Proyecto proyecto) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			ServicioColumna servicioColumna= new ServicioColumna();
			List <Columna> listaColumnas = servicioColumna.recuperarTodosColumna(proyecto);
			
			for(int i = 0; i< listaColumnas.size(); i++) {
				servicioColumna.borrarColumna(listaColumnas.get(i));
			}
			

			Proyecto_UsuarioDAO puDAO = trans.getProyecto_UsuarioDAO();
			List<Proyecto_Usuario> listaPU = puDAO.recuperarTodosProyecto_Usuario(proyecto);
			for(int i = 0; i< listaPU.size(); i++) {
				puDAO.borrarProyecto_Usuario(listaPU.get(i));
			}
			
			borrado = proyectoDAO.borrarProyecto(proyecto);
			
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
	public int borrarProyectoById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			borrado = proyectoDAO.borrarProyectoById(id);

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

	
	public Proyecto recuperarProyecto (Proyecto proyecto) throws ServiceException{
		TransaccionesManager trans = null;

		try {
			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			proyecto = proyectoDAO.recuperarProyecto(proyecto);

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
		return proyecto;
	}
	public Proyecto recuperarProyectoById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		Proyecto proyecto=null;
		try {
			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			proyecto = proyectoDAO.recuperarProyectoById(id);

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
		return proyecto;
	}
	public List<Proyecto> recuperarTodosProyecto (Usuario usuario) throws ServiceException{
		TransaccionesManager trans = null;
		List<Proyecto> list = new ArrayList<Proyecto>();
		try {
			trans = new TransaccionesManager();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();

			list = proyectoDAO.recuperarTodosProyectoDeUsuario(usuario);

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
	
	public Boolean usuarioTieneAccesoAProyecto(Proyecto proyecto, Usuario usuario) {
		if (proyecto.getPropietario().getID() == usuario.getID()) {
			return true;
		}

		List<Usuario> participantes = proyecto.getParticipantes();
		for (int i = 0; i < participantes.size(); i++) {
			if (participantes.get(i).getID() == usuario.getID()) {
				return true;
			}
		}

		return false;
	}
}
