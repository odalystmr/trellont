package domain;

import util.Validator;

import java.util.List;

import daos.ProyectoDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.DomainException;
import exceptions.ServiceException;
import servicios.ServicioColumna;
import servicios.ServicioProyecto;
import servicios.ServicioUsuario;


public class Proyecto {

	private  int id;
	private  String nombre;
	private  String descripcion;
	private  Usuario propietario;

	private List<Columna> columnas ;
	private List<Usuario> participantes;
	public Proyecto(){}


	public Proyecto(int id) {this.id = id;}

	public Proyecto(int id, String nombre, String descripcion,
			Usuario propietario) {	
		TransaccionesManager trans=null;
		try {
			trans = new TransaccionesManager();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		ServicioColumna servicioColumna = new ServicioColumna();
		ProyectoDAO proyectoDAO = trans.getProyectoDAO();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.propietario = propietario;
		try {
			this.columnas = servicioColumna.recuperarTodosColumna(this);
		} catch (ServiceException e) {}
		try {
			this.participantes = proyectoDAO.recuperarTodosUsuarioDeProyecto(this);
		} catch (DAOException e) {}		
	}

	public Proyecto(Proyecto proyecto){
		try {
			TransaccionesManager trans = new TransaccionesManager();
			ServicioColumna servicioColumna = new ServicioColumna();
			ProyectoDAO proyectoDAO = trans.getProyectoDAO();
			
			setID(proyecto.id);
			setNombre(proyecto.nombre);
			setDescripcion(proyecto.descripcion);
			setPropietario(proyecto.propietario);   
			setParticipantes(proyectoDAO.recuperarTodosUsuarioDeProyecto(proyecto)) ;
			setColumnas(servicioColumna.recuperarTodosColumna(proyecto)) ;
			} catch (ServiceException e) {
				throw new DomainException("Error"); 
			}catch (DAOException e) {
				throw new DomainException("Error2");
			}		
	}

		public void setColumnas(List<Columna> listaColumnas) {
			this.columnas=listaColumnas;

		}

		public void setParticipantes(List<Usuario> listaParticipantes) {
			this.participantes=listaParticipantes;

		}

		public List<Usuario> getParticipantes() {
			return this.participantes;

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
				throw new DomainException("La longitud del nombre del proyecto no es v�lida.");
			}
		}

		public String getDescripcion(){
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			if (Validator.length(descripcion, 0, 100)) {
				this.descripcion =descripcion.trim();
			} else {
				throw new DomainException("La longitud del descripcion del proyecto no es v�lida.");
			}	
		}
		public Usuario getPropietario() {
			return propietario;
		}

		public void setPropietario(Usuario propietario) {
			if (propietario!=null ) {
				this.propietario = propietario;
			} else {
				throw new DomainException("el propietario del proyecto es obligatorio.");
			}
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Proyecto [ID:" + id + ",Nombre:" + nombre
					+ ",Descripci�n:" + descripcion + ",Propietario: (ID: " + propietario.getID()
					+",Nombre: " + propietario.getNombre() + ",Email: " +propietario.getEmail() 
					+")]";
		}

		public String toJSON() {
			
			String participantesJSON="[";

			if (participantes != null) {
				for(int i = 0; i < participantes.size(); i++) {
					participantesJSON += participantes.get(i).toJSON();

					if (i < participantes.size() - 1) {
						participantesJSON += ",";
					}
				}
			}

			participantesJSON += "]";

			String columnasJSON="[";

			if (columnas != null) {
				for(int i = 0; i < columnas.size(); i++) {
					columnasJSON += columnas.get(i).toJSON();

					if (i < columnas.size() - 1) {
						columnasJSON += ",";
					}
				}
			}

			columnasJSON += "]";
			
			ServicioProyecto servicioProyecto = new ServicioProyecto();
			ServicioUsuario servicioUsuario = new ServicioUsuario();
			Proyecto proyecto= null;

			try {
				proyecto = servicioProyecto.recuperarProyecto(this);
				proyecto.setPropietario(servicioUsuario.recuperarUsuario(proyecto.getPropietario()));
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			propietario = proyecto.getPropietario();

			return "{"
				+ "\"id\": \""+ this.getID() +"\","
				+ "\"nombre\": \"" + this.getNombre() + "\","
				+ "\"descripcion\": \"" + this.getDescripcion() + "\","
				+ "\"propietario\": {"
				+	 "\"id\": \"" + propietario.getID() + "\","
				+	 "\"nombre\": \"" + propietario.getNombre() + "\","
				+	 "\"email\": \"" + propietario.getEmail() + "\""
				+"},"
				+"\"columnas\": " + columnasJSON + ","
				+"\"participantes\": " + participantesJSON + ""
			+ "}";
		}

	}
