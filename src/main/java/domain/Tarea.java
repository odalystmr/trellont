package domain;

import util.Validator;

import java.util.List;

import exceptions.DomainException;
import exceptions.ServiceException;
import servicios.ServicioComentario;
import servicios.ServicioUsuario;


public class Tarea {

	private  int id;
	private  String titulo;
	private  String descripcion;

	private  Proyecto proyecto;
	private  Columna columna;
	private  Usuario creador;
	private  Usuario responsable;
	private List<Comentario> comentarios;

	public Tarea(){}


	public Tarea(int id) {this.id = id;}

	public Tarea(int id, String titulo, String descripcion,
			Proyecto proyecto, Columna columna,  Usuario creador, Usuario responsable) {		
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.proyecto=proyecto;
		this.columna=columna;
		this.creador=creador;
		this.responsable=responsable;
		
	}

	public Tarea(Tarea tarea){
		setID(tarea.id);
		setTitulo(tarea.titulo);
		setDescripcion(tarea.descripcion);
		setProyecto(tarea.proyecto);  
		setColumna(tarea.columna);
		setCreador(tarea.creador);
		setResponsable(tarea.responsable); 	
	}




	public Columna getColumna() {
		return columna;
	}


	public void setColumna(Columna columna) {
		this.columna = columna;
	}


	public Usuario getCreador() {
		return creador;
	}


	public void setCreador(Usuario creador) {
		this.creador = creador;
	}


	public Usuario getResponsable() {
		return responsable;
	}


	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}


	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (Validator.length(titulo, 1, 50)) {
			this.titulo = titulo.trim();
		} else {
			throw new DomainException("La longitud del titulo del Tarea no es v�lida.");
		}
	}

	public String getDescripcion(){
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		if (Validator.length(descripcion, 0, 100)) {
			this.descripcion =descripcion.trim();
		} else {
			throw new DomainException("La longitud del descripcion del Tarea no es v�lida.");
		}	
	}
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		if (proyecto!=null ) {
			this.proyecto= proyecto;
		} else {
			throw new DomainException("el proyecto del Tarea es obligatorio.");
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tarea [ID:" + id + ",titulo:" + titulo
				+ ",Descripcion:" + descripcion + ",Proyecto ID: " + proyecto.getID()
				+", Creador ID: " + creador.getID() + ", Responsable ID: " +responsable.getID() 
				+",Columna ID: " +columna.getID()+ "]";
	}

	public String toJSON() {
		ServicioUsuario servicioUsuario=new ServicioUsuario();

		try {
			creador = servicioUsuario.recuperarUsuario(creador);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		String responsableJSON = "";
		try {
			if(responsable != null && responsable.getID() != 0) {
				responsable = servicioUsuario.recuperarUsuario(responsable);
				responsableJSON = "\"responsable\":" + responsable.toJSON() + ",";
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		String comentariosJSON = "";
		try {
			ServicioComentario servicioComentario = new ServicioComentario();
			comentarios = servicioComentario.recuperarTodosComentario(this);

			if (comentarios != null && comentarios.size() > 0) {
				comentariosJSON = "\"comentarios\": [";

				for	(int i = 0; i < comentarios.size(); i++) {
					comentariosJSON += comentarios.get(i).toJSON();

					if (i < comentarios.size() - 1) {
						comentariosJSON += ",";
					}
				}

				comentariosJSON += "],";
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return "{"
				+ "\"id\": \""+ id +"\","
				+ "\"titulo\": \"" + titulo + "\","
				+ "\"descripcion\": \"" + descripcion + "\","
				+ "\"proyecto_id\": \"" + proyecto.getID() + "\"," //ya tengo los datos del proyecto
				+ "\"creador\": {"
				+	 "\"id\": \""+ creador.getID() +"\","
				+	 "\"nombre\": \""+ creador.getNombre() +"\","
				+	 "\"email\": \""+ creador.getEmail() +"\""
				+ "},"
				+ responsableJSON
				+ comentariosJSON
				+ "\"columna\": \"" + columna.getID() + "\""
			+ "}"
		 ;
			
	}
}
