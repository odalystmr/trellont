package recursos;

public class DbQuery {
// MODIFICAR SIN ACABAR
	
	//usuario		
	private static final String InsertarUsuario = "Insert into usuarios(nombre, email, password) values(?,?,?)";
	private static final String ModificarUsuario = "update usuarios set nombre = ?, email = ?, password = ?, token = ? where id = ?";
	private static final String BorrarUsuario = "delete from usuarios where id = ?";
	private static final String BorrarUsuarioByEmail = "delete from usuarios where email = ?";
	private static final String RecuperarUsuario = "select id, nombre, email, password, token from usuarios where id = ?";
	private static final String RecuperarUsuarioEmail = "select id, nombre, email, password, token from usuarios where email = ?";
	private static final String RecuperarUsuarioToken = "select id, nombre, email, password, token from usuarios where token = ?";
	private static final String RecuperarTodosUsuario= "select id, nombre, email, password, token from usuarios  order by nombre";

	//proyecto
	private static final String InsertarProyecto= "Insert into proyectos(id, nombre, descripcion, propietario_id) values(?,?,?,?)";
	private static final String ModificarProyecto = "update proyectos set nombre = ?, descripcion = ? where id = ?";
	private static final String RelacionarParticipante = "insert into proyectos_usuarios (proyecto_id, usuario_id) values (?, ?)";
	private static final String BorrarProyecto= "delete from proyectos where id = ?";
	private static final String RecuperarProyecto = "select id, nombre, descripcion, propietario_id from proyectos where id = ?";
	private static final String RecuperarTodosProyecto= "select id, nombre, descripcion, propietario_id from proyectos order by nombre";
	private static final String RecuperarTodosProyectoDeUsuario= " select p.id, p.nombre, p.descripcion, propietario_id"
			+ " from proyectos as p"
			+ " left join proyectos_usuarios as pu"
			+ " on pu.proyecto_id = p.id" 
			+ " where propietario_id = ?" //propietario
			+ " or usuario_id = ?" //participante
			+ " order by p.nombre";
	private static final String RecuperarTodosUsuarioDeProyecto= " select id, nombre, email"
			+ " from usuarios as u"
			+ " left join proyectos_usuarios as pu"
			+ " on pu.usuario_id = u.id" 
			+ " where pu.proyecto_id = ?";



	//columna
	private static final String InsertarColumna= "Insert into columnas(id, nombre, proyecto_id) values(?,?,?)";
	private static final String ModificarColumna= "update columnas set nombre = ? where id = ?";
	private static final String BorrarColumna= "delete from columnas where id = ?";
	private static final String RecuperarColumna = "select id, nombre, proyecto_id from columnas where id = ?";
	private static final String RecuperarTodosColumna= "select id, nombre, proyecto_id from columnas where proyecto_id = ? order by id";

	//tarea
	private static final String InsertarTarea= "Insert into tareas(id, titulo, descripcion, proyecto_id, columnas_id, creador_id, responsable_id) values(?,?,?,?,?,?,?)";
	private static final String ModificarTarea= "update tareas set titulo = ?, descripcion = ?, proyecto_id = ?, columnas_id = ?, creador_id = ?, responsable_id = ? where id = ?";
	private static final String BorrarTarea= "delete from tareas where id = ?";
	private static final String RecuperarTarea= "select id, titulo, descripcion, proyecto_id, columnas_id, creador_id, responsable_id from tareas where id = ?";
	private static final String RecuperarTodosTarea= "select id, titulo, descripcion, proyecto_id, columnas_id, creador_id, responsable_id from tareas where columnas_id = ? order by id";

	//comentario
	private static final String InsertarComentario= "Insert into comentarios(id, comentario, tarea_id, usuario_id) values(?,?,?,?)";
	private static final String ModificarComentario= "update comentarios set comentario = ? where id = ?";
	private static final String BorrarComentario= "delete from comentarios where id = ?";
	private static final String RecuperarComentario= "select id, comentario, tarea_id, usuario_id from comentarios where id = ?";
	private static final String RecuperarTodosComentario= "select id, comentario, tarea_id, usuario_id from comentarios where tarea_id = ? order by id";

	//proyecto_usuario
	private static final String InsertarProyecto_Usuario= "Insert into proyectos_usuarios (proyecto_id, usuario_id) values(?,?)";
	private static final String ModificarProyecto_Usuario= "update proyectos_usuarios set proyecto_id = ? where proyecto_id = ?"; //???????
	private static final String BorrarProyecto_Usuario= "delete from proyectos_usuarios where proyecto_id = ?";
	private static final String RecuperarProyecto_Usuario = "select proyecto_id, usuario_id from proyectos_usuarios where id = ?";
	private static final String RecuperarTodosProyecto_Usuario= "select proyecto_id, usuario_id from proyectos_usuarios where proyecto_id = ? order by proyecto_id";
	
	//metodos usuario
	public static String getInsertarUsuario() {		
		return InsertarUsuario;
	}	
	public static String getModificarUsuario() {	
		return ModificarUsuario;
	}		
	public static String getBorrarUsuario() {	
		return BorrarUsuario;
	}
	public static String getRecuperarUsuario() {	
		return RecuperarUsuario;
	}
	public static String getRecuperarUsuarioEmail() {	
		return RecuperarUsuarioEmail;
	}
	public static String getRecuperarUsuarioToken() {	
		return RecuperarUsuarioToken;
	}
	public static String getRecuperarTodosUsuario() {		
		return RecuperarTodosUsuario;
	}

	//metodos proyecto
	public static String getInsertarProyecto() {		
		return InsertarProyecto;
	}
	public static String getModificarProyecto() {	
		return ModificarProyecto;
	}
	public static String getRelacionarParticipante() {	
		return RelacionarParticipante;
	}		
	public static String getBorrarProyecto() {	
		return BorrarProyecto;
	}		
	public static String getRecuperarProyecto() {	
		return RecuperarProyecto;
	}		
	public static String getRecuperarTodosProyecto() {		
		return RecuperarTodosProyecto;
	}
	public static String getRecuperarTodosProyectoDeUsuario() {
		return RecuperarTodosProyectoDeUsuario;
	}
	public static String getRecuperarTodosUsuarioDeProyecto() {
		return RecuperarTodosUsuarioDeProyecto;
	}

	//metedos columnas
	public static String getInsertarColumna() {		
		return InsertarColumna;
	}	
	public static String getModificarColumna() {	
		return ModificarColumna;
	}		
	public static String getBorrarColumna() {	
		return BorrarColumna;
	}		
	public static String getRecuperarColumna() {	
		return RecuperarColumna;
	}		
	public static String getRecuperarTodosColumna() {		
		return RecuperarTodosColumna;
	}
	
	//metodos tareas
	public static String getInsertarTarea() {
		return InsertarTarea;
	}
	public static String getModificarTarea() {
		return ModificarTarea;
	}
	public static String getBorrarTarea() {
		return BorrarTarea;
	}
	public static String getRecuperarTarea() {
		return RecuperarTarea;
	}
	public static String getRecuperarTodosTarea() {
		return RecuperarTodosTarea;
	}
	
	//metodos comentarios
	public static String getInsertarComentario() {
		return InsertarComentario;
	}
	public static String getModificarComentario() {
		return ModificarComentario;
	}
	public static String getBorrarComentario() {
		return BorrarComentario;
	}
	public static String getRecuperarComentario() {
		return RecuperarComentario;
	}
	public static String getRecuperarTodosComentario() {
		return RecuperarTodosComentario;
	}
	
	//metodos proyecto_usuario
	public static String getInsertarProyecto_Usuario() {
		return InsertarProyecto_Usuario;
	}
	public static String getModificarProyecto_Usuario() {
		return ModificarProyecto_Usuario;
	}
	public static String getBorrarProyecto_Usuario() {
		return BorrarProyecto_Usuario;
	}
	public static String getRecuperarProyecto_Usuario() {
		return RecuperarProyecto_Usuario;
	}
	public static String getRecuperarTodosProyecto_Usuario() {
		return RecuperarTodosProyecto_Usuario;
	}


}
