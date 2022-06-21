package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServicioComentario;
import servicios.ServicioTarea;
import servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import domain.Comentario;
import domain.Usuario;
import domain.Tarea;
import exceptions.DomainException;
import exceptions.ServiceException;


/**
 * Servlet implementation class ServletComentario
 */
@WebServlet("/comentarios/*")
public class ServletComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletComentario() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("", 401, response);
			return;
		}

		ServicioComentario servicioComentario = new ServicioComentario();
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		ServicioTarea servicioTarea = new ServicioTarea();
		Comentario comentario = new Comentario();
		Usuario usuario;
		Tarea tarea;

		try {
			tarea = servicioTarea.recuperarTareaById(Integer.parseInt(request.getParameter("tarea_id")));
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id de la tarea del comentario debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("Tarea del comentario no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		//datos del comentario
		try {
			comentario.setComentario(request.getParameter("comentario"));
			comentario.setTarea(tarea);
			comentario.setUsuario(usuarioSesionIniciada);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un comentario", 400, response);
			return;
		}

		//Insertar comentario
		try {
			servicioComentario.insertarComentario(comentario);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un comentario", 500, response);
			return;
		}
		Utilidades.devolver(comentario.getTarea().toJSON(), 200, response);
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioComentario servicioComentario = new ServicioComentario();
		Comentario comentario;
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int comentarioId = Integer.parseInt(splits[1]);
		try {
			comentario = servicioComentario.recuperarComentarioById(comentarioId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un comentario", 500, response);
			return;
		}

		//datos del comentario
		try {
			comentario.setComentario(request.getParameter("comentario"));
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un comentario", 400, response);
			return;
		}

		//modificar comentario
		try {
			servicioComentario.modificarComentario(comentario);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error modificando un comentario", 500, response);
			return;
		}
		Utilidades.devolver("Comentario modificado", 200, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioComentario servicioComentario = new ServicioComentario();
		Comentario comentario = new Comentario ();

		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int comentarioId = Integer.parseInt(splits[1]);
		try {
			comentario = servicioComentario.recuperarComentarioById(comentarioId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un comentario", 500, response);
			return;
		}

		//borrar comentario
		try {
			if(servicioComentario.borrarComentario(comentario)==0) {
				Utilidades.devolver("No hay comentario que borrar", 400, response);
			}
			else {
				servicioComentario.borrarComentario(comentario); //??????????????????????????????????????
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error borrando comentario", 500, response);
			return;
		}
		Utilidades.devolver("Comentario borrado", 200, response);
	}
}

