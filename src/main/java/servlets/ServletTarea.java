package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import servicios.ServicioColumna;
import servicios.ServicioProyecto;
import servicios.ServicioTarea;
import servicios.ServicioUsuario;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import domain.Tarea;
import domain.Proyecto;
import domain.Columna;
import domain.Usuario;
import exceptions.DomainException;
import exceptions.ServiceException;


/**
 * Servlet implementation class ServletTarea
 */
@WebServlet("/tareas/*")
public class ServletTarea extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletTarea() {
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

		ServicioTarea servicioTarea = new ServicioTarea();
		Tarea tarea = new Tarea();

		String urlInfo = request.getPathInfo();

		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length != 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		//recuperar una tarea
		int tareaId = Integer.parseInt(splits[1]);

		try {
			tarea=servicioTarea.recuperarTareaById(tareaId);
			if (tarea!=null) {
				Utilidades.devolver(tarea.toJSON(), 200, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error recuperando una tarea", 500, response);
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

		ServicioTarea servicioTarea = new ServicioTarea();

		ServicioProyecto servicioProyecto = new ServicioProyecto();
		ServicioColumna servicioColumna = new ServicioColumna();
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		
		Tarea tarea = new Tarea();

		Proyecto proyecto;
		Columna columna;
		Usuario responsable;

		try {
			proyecto = servicioProyecto.recuperarProyectoById(Integer.parseInt(request.getParameter("proyecto_id")));
		
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id del proyecto de la tarea debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("proyecto de la tarea no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		//recuperar columna de la tarea

		try {
			columna = servicioColumna.recuperarColumnaById(Integer.parseInt(request.getParameter("columna_id")));
			
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id del columna de la tarea debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("columna de la tarea no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		int responsableId = 0;
		try {
			responsableId = Integer.parseInt(request.getParameter("responsable_id"));
		} catch (NumberFormatException e1) {
		}

		try {
			responsable = servicioUsuario.recuperarUsuarioById(responsableId);
		}catch (ServiceException e1) {
			Utilidades.devolver("usuario responsable de la tarea no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		//datos del usuario
		try {
			tarea.setTitulo(request.getParameter("titulo"));
			tarea.setDescripcion(request.getParameter("descripcion"));
			tarea.setProyecto(proyecto);
			tarea.setColumna(columna);
			
			tarea.setCreador(usuarioSesionIniciada);
			tarea.setResponsable(responsable);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un tarea", 400, response);
			return;
		}

		//Insertar tarea
		try {
			servicioTarea.insertarTarea(tarea);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un tarea", 500, response);
			return;
		}
		Utilidades.devolver("Tarea creado", 200, response);
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioTarea servicioTarea = new ServicioTarea();
		ServicioColumna servicioColumna = new ServicioColumna();
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Tarea tarea;
		Columna columna;
		Usuario responsable;
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int tareaId = Integer.parseInt(splits[1]);
		try {
			tarea = servicioTarea.recuperarTareaById(tareaId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un tarea", 500, response);
			return;
		}
		//recuperar columna
		try {
			int columnaId;

			try {
				columnaId = Integer.parseInt(request.getParameter("columna"));
			} catch(NumberFormatException e) {
				columnaId = tarea.getColumna().getID();
			}

			columna = servicioColumna.recuperarColumnaById(columnaId);
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id del columna de la tarea debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("columna de la tarea no existe", 404, response);
			e1.printStackTrace();
			return;
		}
		//recuperar responsable

		try {
			int responsableId;

			try {
				responsableId = Integer.parseInt(request.getParameter("responsable"));
			} catch(NumberFormatException e) {
				responsableId = tarea.getResponsable().getID();
			}

			responsable = servicioUsuario.recuperarUsuarioById(responsableId);
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id del usuario responsable de la tarea debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("Usuario responsable de la tarea no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		String titulo = request.getParameter("titulo") != null ? request.getParameter("titulo") : tarea.getTitulo();
		String descripcion = request.getParameter("descripcion") != null ? request.getParameter("descripcion") : tarea.getDescripcion();

		//datos del tarea
		try {
			tarea.setTitulo(titulo);
			tarea.setDescripcion(descripcion);
			tarea.setColumna(columna);
			tarea.setResponsable(responsable);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un tarea", 400, response);
			return;
		}

		//modificar tarea
		try {
			servicioTarea.modificarTarea(tarea);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error modificando un tarea", 500, response);
			return;
		}
		Utilidades.devolver(tarea.toJSON(), 200, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioTarea servicioTarea = new ServicioTarea();
		Tarea tarea = new Tarea();

		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int tareaId = Integer.parseInt(splits[1]);
		try {
			tarea = servicioTarea.recuperarTareaById(tareaId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un tarea", 500, response);
			return;
		}

		//borrar tarea
		try {
			if(servicioTarea.borrarTarea(tarea)==0) {
				Utilidades.devolver("No hay tarea que borrar", 400, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error borrando tarea", 500, response);
			return;
		}
		Utilidades.devolver("Tarea borrado", 200, response);
	}
}

