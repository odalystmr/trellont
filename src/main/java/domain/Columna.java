package domain;

import util.Validator;

import java.util.List;

import exceptions.DomainException;
import exceptions.ServiceException;
import servicios.ServicioTarea;


public class Columna {

	private  int id;
	private  String nombre;
	private  Proyecto proyecto;

	private List<Tarea> tareas;

	public Columna() {};
	public Columna(int id) {this.id = id;}

	public Columna(int id, String nombre, Proyecto proyecto) {		
		this.id = id;
		this.nombre = nombre;
		this.proyecto = proyecto;

		ServicioTarea servicioTarea = new ServicioTarea();
		try {
			this.tareas = servicioTarea.recuperarTodosTarea(this);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Columna(Columna columna){
		setID(columna.id);
		setNombre(columna.nombre);
		setProyecto(columna.proyecto);
		ServicioTarea servicioTarea = new ServicioTarea();
		try {
			setTareas(servicioTarea.recuperarTodosTarea(this));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (Validator.length(nombre, 1, 50)) {
			this.nombre = nombre.trim();
		} else {
			throw new DomainException("La longitud del nombre de la columna no es v�lida.");
		}
	}


	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		if (proyecto!=null ) {
			this.proyecto = proyecto;
		} else {
			throw new DomainException("el proyecto de la columna es obligatorio.");
		}
	}
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	public String toString() {
		return "Columna [ID:" + id + ",Nombre:" + nombre
				+ ",Proyecto: (ID: " + proyecto.getID()
				+",Nombre: " + proyecto.getNombre() + ",Descripci�n: " +proyecto.getDescripcion() 
				+",Propietario ID: " +proyecto.getPropietario() + ")]";
	}	
	public String toJSON() {
		String tareasJSON = "[";

		if (tareas != null) {
			for (int i = 0; i < tareas.size(); i++) {
				tareasJSON += tareas.get(i).toJSON();

				if (i < tareas.size() - 1) {
					tareasJSON += ",";
				}
			}
		}

		tareasJSON += "]";

		return "{"
			+ "\"id\": \""+ id +"\","
			+ "\"nombre\": \"" + nombre + "\","
			+ "\"proyecto_id\": \"" + proyecto.getID() + "\"," //ya tengo los datos del proyecto
			+ "\"tareas\": " + tareasJSON
		+ "}";
	}

}
