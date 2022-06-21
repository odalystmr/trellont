package domain;

import exceptions.DomainException;


public class Proyecto_Usuario {

	private  Proyecto proyecto_id;
	private  Usuario usuario_id;

	public Proyecto_Usuario(){}


	public Proyecto_Usuario(Proyecto proyecto_id, Usuario usuario_id) {		
		this.proyecto_id=proyecto_id;//.getID();
	  this.usuario_id=usuario_id;
	}

	public Proyecto_Usuario(Proyecto_Usuario proyectos_usuarios){
		setProyecto_id(proyectos_usuarios.proyecto_id);
		setUsuario_id(proyectos_usuarios.usuario_id);
	}

public Proyecto getProyecto_id() {
		return proyecto_id;
	}

	public void setProyecto_id(Proyecto proyecto_id) {
		if (proyecto_id!=null ) {
			this.proyecto_id = proyecto_id;
		} else {
			throw new DomainException("el proyecto del Proyectos_Usuarios es obligatorio.");
		}
	}
	

	public Usuario getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Usuario usuario_id) {
		if (usuario_id!=null ) {
			this.usuario_id = usuario_id;
		} else {
			throw new DomainException("el usuario del Proyectos_Usuarios es obligatorio.");
		}
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Proyectos_Usuarios [Proyecto ID:" + proyecto_id.getID()
				+",Usuario ID: " + usuario_id.getID()+ "]";
	}	

}
