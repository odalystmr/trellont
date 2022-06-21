package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import servicios.ServicioProyecto;
import servicios.ServicioUsuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import domain.Proyecto;
import domain.Usuario;
import exceptions.DomainException;
import exceptions.ServiceException;


/**
 * Servlet implementation class ServletProyecto
 */

@WebServlet({
	"/proyectos",
	"/proyectos/*"	
}) //asi tengo 2 urls
public class ServletProyecto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletProyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		ServicioProyecto servicioProyecto = new ServicioProyecto();
		Proyecto proyecto = new Proyecto();
		List<Proyecto> listaProyecto = new ArrayList<Proyecto>();

		String urlInfo = request.getPathInfo();

		//error: /proyectos/64/2sdasd 
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		//caso /proyectos/  o /proyectos  Utilidades.devolver todos
		if(urlInfo == null || urlInfo.equals("/")){
			//Recuperar a todos los proyectos
			try {
				listaProyecto=servicioProyecto.recuperarTodosProyecto(usuarioSesionIniciada);
				//ver los clientes
				if(listaProyecto.size()!=0){
					String salida="[";
					for(int i=0;i<listaProyecto.size();i++){  
						salida+=listaProyecto.get(i).toJSON();
						if(i < listaProyecto.size() - 1) {
							salida+=",";
						}
					}	
					salida +="]";
					Utilidades.devolver(salida,200, response);
					
				} else {
					Utilidades.devolver("[]", 500, response);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				Utilidades.devolver("Error recuperando un proyecto", 500, response);
				return;
			}
			return;
		}

		//recuperar un proyecto
		int proyectoId = Integer.parseInt(splits[1]);

		try {
			proyecto=servicioProyecto.recuperarProyectoById(proyectoId);

			if (!servicioProyecto.usuarioTieneAccesoAProyecto(proyecto, usuarioSesionIniciada)) {
				Utilidades.devolver("", 401, response);
				return;
			}

			if (proyecto!=null) {
				Utilidades.devolver(proyecto.toJSON(), 200, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error recuperando un proyecto", 500, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioProyecto servicioProyecto = new ServicioProyecto();
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Proyecto proyecto = new Proyecto();
		Usuario usuario;
		//recuperar datos del usuario del proyecto

		//datos del proyecto
		try {
			proyecto.setNombre(request.getParameter("nombre"));

			proyecto.setDescripcion(request.getParameter("descripcion"));

			proyecto.setPropietario(usuarioSesionIniciada);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un proyecto", 400, response);
			return;
		}

		//Insertar proyecto
		try {
			servicioProyecto.insertarProyecto(proyecto);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un proyecto", 500, response);
			return;
		}
		Utilidades.devolver("Proyecto creado", 200, response);
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioProyecto servicioProyecto = new ServicioProyecto();
		Proyecto proyecto;
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int proyectoId = Integer.parseInt(splits[1]);
		try {
			proyecto = servicioProyecto.recuperarProyectoById(proyectoId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un proyecto", 500, response);
			return;
		}

		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");

		if(nombre== null || nombre.isEmpty() || nombre.equals("null")) {
			nombre = proyecto.getNombre();
		}

		if(descripcion == null || descripcion.isEmpty() || descripcion.equals("null")) {
			descripcion = proyecto.getDescripcion();
		}

		//datos del proyecto
		try {
			proyecto.setNombre(nombre);
			proyecto.setDescripcion(descripcion);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un proyecto", 400, response);
			return;
		}

		List<Usuario> participantes = new ArrayList<Usuario>();

		try {
			String emailParticipante = request.getParameter("email_participante");

			ServicioUsuario servicioUsuario = new ServicioUsuario();
			Usuario participante = servicioUsuario.recuperarUsuarioByEmail(emailParticipante);

			participantes.add(participante);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//modificar proyecto
		try {
			servicioProyecto.modificarProyecto(proyecto);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error modificando un proyecto", 500, response);
			return;
		}

		try {
			servicioProyecto.relacionarParticipantes(proyecto, participantes);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error relacionando participantes con el proyecto", 500, response);
			return;
		}
		Utilidades.devolver(proyecto.toJSON(), 200, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioProyecto servicioProyecto = new ServicioProyecto();
		Proyecto proyecto = new Proyecto();

		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int proyectoId = Integer.parseInt(splits[1]);
		try {
			proyecto = servicioProyecto.recuperarProyectoById(proyectoId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un proyecto", 500, response);
			return;
		}

		if (proyecto == null) {
			Utilidades.devolver("El proyecto no existe", 404, response);
		}

		//borrar proyecto
		try {
			if(servicioProyecto.borrarProyecto(proyecto) == 0) {
				Utilidades.devolver("No se ha borrado el proyecto", 500, response);
				return;
			}

			Utilidades.devolver("Proyecto borrado", 200, response);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error borrando proyecto", 500, response);
			return;
		}
	}
}

