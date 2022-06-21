package domain;

import util.Validator;
import exceptions.DomainException;
import exceptions.ServiceException;
import servicios.ServicioUsuario;

public class Usuario {
 
	private  int id;
	private  String nombre;
	private  String email;
	private  String password;
	private  String token = null;
	
	
	public Usuario(){}
	
	
public Usuario(int id) {this.id = id;}
	/**
	 * @param id
	 * @param nombre
	 * @param email
	 * @param password
	 */
	public Usuario(int id, String nombre, String email,
			String password, String token) {		
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.token = token;
		
	}
	
	public Usuario(Usuario usuario){
    	setID(usuario.id);
    	setNombre(usuario.nombre);
    	setEmail(usuario.email);
    	setPassword(usuario.password);
    	setToken(usuario.token);
    }

	public String getToken() {
		return this.token;		
	}

	public void setToken(String token) {
		this.token= token;		
	}

	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		//Clausula de guarda
		if (!Validator.length(nombre, 1, 50)) {
			throw new DomainException("La longitud del nombre del usuario no es válida. " + nombre);
		}

		this.nombre = nombre.trim();
	}
	
	public String getEmail(){
			return email;
	}	
	
	public void setEmail(String email) {
		if(email==null || email.trim().length()==0){
			this.email=null;
		}
		else {
			if (Validator.email(email, 1, 30)) {
			this.email = email.trim();
		    } else {
			throw new DomainException("El email no es válido.");
		    }
		}	
	}
	
	
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password) {
		if (Validator.length(password, 1, 30)) {
			this.password = password.trim();
		} else {
			throw new DomainException("La longitud de password del usuario no es válida.");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Usuario [ID: " + id + ",Nombre: " + nombre
				+ ",Email: " + email + ",Password: " + password +"]";
	}	
	
	public String toJSON() {
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario;

		try {
			usuario = servicioUsuario.recuperarUsuarioById(this.getID());

			return "{"
				+ "\"id\": \""+ usuario.getID() +"\","
				+ "\"nombre\": \"" + usuario.getNombre() + "\","
				+ "\"email\": \"" + usuario.getEmail() + "\""
		+ "}";
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
}
