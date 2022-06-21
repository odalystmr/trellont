package servicios;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import domain.Usuario;
import daos.UsuarioDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;
import servlets.Utilidades;

public class ServicioUsuario {

	public ServicioUsuario() {
		// TODO Auto-generated constructor stub
	}
	public void insertarUsuario(Usuario usuario) throws ServiceException{
		TransaccionesManager trans = null;
		try {

			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			usuarioDAO.insertarUsuario(usuario);


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
	public int modificarUsuario(Usuario usuario) throws ServiceException{
		TransaccionesManager trans = null;
		 int modificar=0;
		try {

			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			modificar=usuarioDAO.modificarUsuario(usuario);


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
	public int borrarUsuario(Usuario usuario) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			borrado = usuarioDAO.borrarUsuario(usuario);

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
	public int borrarUsuarioById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		int borrado=0;
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			borrado = usuarioDAO.borrarUsuarioById(id);

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
	public Usuario recuperarUsuario(Usuario usuario) throws ServiceException{
		TransaccionesManager trans = null;

		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			usuario = usuarioDAO.recuperarUsuario(usuario);


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
		return usuario;
	}
	public Usuario recuperarUsuarioById(int id) throws ServiceException{
		TransaccionesManager trans = null;
		Usuario usuario=null;
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			usuario = usuarioDAO.recuperarUsuarioById(id);


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
		return usuario;
	}
	public Usuario recuperarUsuarioByEmail(String email) throws ServiceException{
		TransaccionesManager trans = null;
		Usuario usuario=null;
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			usuario = usuarioDAO.recuperarUsuarioByEmail(email);


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
		return usuario;
	}
	public Usuario recuperarUsuarioByToken(String token) throws ServiceException{
		TransaccionesManager trans = null;
		Usuario usuario=null;
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			usuario = usuarioDAO.recuperarUsuarioByToken(token);


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
		return usuario;
	}
	public List<Usuario> recuperarTodosUsuario() throws ServiceException{
		TransaccionesManager trans = null;
		List<Usuario> list = new ArrayList<Usuario>();
		try {
			trans = new TransaccionesManager();
			UsuarioDAO usuarioDAO = trans.getUsuarioDAO();
			list = usuarioDAO.recuperarTodosUsuario();


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
	public String iniciarSesion(String email, String contra) throws ServiceException{
		try {
			Usuario usuario = this.recuperarUsuarioByEmail(email);
			
			if(!usuario.getPassword().equals(contra)) {

				throw new ServiceException("Password incorrecta");
			}

			if (usuario.getToken() == null) {
				String token = this.generaTokenAleatorio();

				usuario.setToken(token);
			}

			this.modificarUsuario(usuario);
			
			return usuario.getToken();
		}catch (ServiceException e) {
			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());
			}
			//CLAUSULA DE GUARDA
			//Si salta el error del If estas lineas no se van a ejecutar asi que no hace falta poner un Else. 
			//Queda m�s lindo
			throw new ServiceException(e.getMessage(),e);//Error interno
		}
		
	}

	public Usuario tieneSesionIniciada(String token) {
		try {
			Usuario usuario = this.recuperarUsuarioByToken(token);

			if(usuario == null) {
				return null;
			}

			return usuario;
		} catch (ServiceException e) {
			return null;
		}
	}

	//Esto ha salido de stackoverflow. A mi no me preguntes <3 uwu
	//identificador unico de nosequ�
	private String generaTokenAleatorio() {
		return UUID.randomUUID().toString();
    }
}
