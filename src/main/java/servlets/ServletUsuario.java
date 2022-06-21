
package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import exceptions.DomainException;
import exceptions.ServiceException;

import domain.Usuario;
import servicios.ServicioUsuario;
import servlets.Utilidades;



/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/usuarios/*")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesión", 401, response);
			return;
		}

		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario = new Usuario();
		List<Usuario> listaUsuario = new ArrayList<Usuario>();

		String urlInfo = request.getPathInfo();

		//error: /usuarios/64/2sdasd 
		String[] splits = urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		//caso /usuarios/  o /usuarios  Utilidades.devolver todos
		if(urlInfo == null || urlInfo.equals("/")){
			//Recuperar a todos los usuarios
			try {
				listaUsuario=servicioUsuario.recuperarTodosUsuario();
				//ver los clientes
				if(listaUsuario.size()!=0){
					String salida = null;
					for(int i=0;i<listaUsuario.size();i++){  
						salida+=listaUsuario.get(i).toString();
					}	  
					Utilidades.devolver(salida,200, response);
				} else {
					Utilidades.devolver("No hay usuarios", 500, response);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				Utilidades.devolver("Error recuperando un usuario", 500, response);
				return;
			}
			Utilidades.devolver("Lista de usuarios recuperada", 200, response);
			return;
		}

		//recuperar un usuario
		int usuarioId = Integer.parseInt(splits[1]);

		try {
			usuario=servicioUsuario.recuperarUsuarioById(usuarioId);
			if (usuario!=null) {
				Utilidades.devolver(usuario.toString(), 200, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error recuperando un usuario", 500, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesión", 401, response);
			return;
		}

		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario = new Usuario();

		//datos del usuario
		try {
			usuario.setNombre(request.getParameter("nombre"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setPassword(request.getParameter("password"));
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un usuario", 400, response);
			return;
		}

		//Insertar usuario
		try {
			servicioUsuario.insertarUsuario(usuario);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un usuario", 500, response);
			return;
		}
		Utilidades.devolver("Usuario creado", 200, response);
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesión", 401, response);
			return;
		}

		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario;
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int usuarioId = Integer.parseInt(splits[1]);
		try {
			usuario = servicioUsuario.recuperarUsuarioById(usuarioId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un usuario", 500, response);
			return;
		}

		//datos del usuario
		try {
			usuario.setNombre(request.getParameter("nombre"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setPassword(request.getParameter("password"));
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un usuario", 400, response);
			return;
		}

		//modificar usuario
		try {
			servicioUsuario.modificarUsuario(usuario);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error modificando un usuario", 500, response);
			return;
		}
		Utilidades.devolver("Usuario modificado", 200, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesión", 401, response);
			return;
		}

		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario = new Usuario();
		
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int usuarioId = Integer.parseInt(splits[1]);
		try {
			usuario = servicioUsuario.recuperarUsuarioById(usuarioId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un usuario", 500, response);
			return;
		}

		//borrar usuario
		try {
			if(servicioUsuario.borrarUsuario(usuario)==0) {
				Utilidades.devolver("No hay usuario que borrar", 400, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error borrando usuario", 500, response);
			return;
		}
		Utilidades.devolver("Usuario borrado", 200, response);
	}
}



