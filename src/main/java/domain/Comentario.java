package domain;

import util.Validator;
import exceptions.DomainException;


public class Comentario {

	private int id;
	private String comentario;
	private Tarea tarea;
	private Usuario usuario;

	public Comentario(){}

	public Comentario(int id) {this.id = id;}
	
	public Comentario(int id, String comentario, Tarea tarea, Usuario usuario) {		
		this.id = id;
		this.comentario = comentario;
		this.tarea = tarea;
		this.usuario = usuario;
	}

	public Comentario(Comentario comentario){
		setID(comentario.id);
		setComentario(comentario.comentario);
		setTarea(comentario.tarea);   	
		setUsuario(comentario.usuario);   	
	}


	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		if (Validator.length(comentario, 1, 200)) {
			this.comentario= comentario.trim();
		} else {
			throw new DomainException("La longitud del comentario no es válida.");
		}
	}

	
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		if (tarea!=null ) {
			this.tarea = tarea;
		} else {
			throw new DomainException("la tarea del comentario es obligatoria.");
		}
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		if (usuario!=null ) {
			this.usuario = usuario;
		} else {
			throw new DomainException("el usuario del comentario es obligatorio.");
		}
	}

	public String toString() {
		return "Comentario [ID:" + id + ",comentario:" + comentario
				+ ",Tarea: (ID: " + tarea.getID()
				+"), Usuario: (" + usuario.getID() + ")]";
	}

	public String toJSON() {
		return "{"
				+ "\"comentario\": \"" + this.getComentario() + "\","
				+ "\"usuario\": " + this.getUsuario().toJSON()
			+ "}";
	}
}
