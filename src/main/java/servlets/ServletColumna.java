package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServicioColumna;
import servicios.ServicioProyecto;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import domain.Columna;
import domain.Proyecto;
import domain.Usuario;
import exceptions.DomainException;
import exceptions.ServiceException;


/**
 * Servlet implementation class ServletColumna
 */
@WebServlet("/columnas/*")
public class ServletColumna extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletColumna() {
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

		ServicioColumna servicioColumna = new ServicioColumna();
		ServicioProyecto servicioProyecto = new ServicioProyecto ();
		Columna columna = new Columna();
		Proyecto proyecto = new Proyecto ();
		List<Columna> listaColumna = new ArrayList<Columna>();

		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		try {
			proyecto = servicioProyecto.recuperarProyectoById(Integer.parseInt(splits[1]));
		} catch (NumberFormatException e) {
			Utilidades.devolver("ID de proyecto invalido. " + e.getMessage(), 400, response);
			return;
		} catch (ServiceException e) {
			Utilidades.devolver("Proyecto no existe. " + e.getMessage(), 404, response);
			return;
		}
		
		//caso /columnas/  o /columnas  devolver todos
		if(urlInfo == null || urlInfo.equals("/")){
			//Recuperar a todos los columnas
			try {
				
				listaColumna=servicioColumna.recuperarTodosColumna(proyecto);
				//ver los clientes
				if(listaColumna.size()!=0){
					String salida="[";
					for(int i=0;i<listaColumna.size();i++){  
						salida+=listaColumna.get(i).toJSON();
						if(i < listaColumna.size() - 1) {
							salida+=",";
						}
					}	
					salida +="]";
					Utilidades.devolver(salida,200, response);
				} else {
					Utilidades.devolver("No hay columnas", 500, response);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				Utilidades.devolver("Error recuperando un columna", 500, response);
				return;
			}
			Utilidades.devolver("Lista de columnas recuperada", 200, response);
			return;
		}

		//recuperar un columna
		int columnaId = Integer.parseInt(splits[1]);

		try {
			columna=servicioColumna.recuperarColumnaById(columnaId);
			if (columna!=null) {
				Utilidades.devolver(columna.toJSON(), 200, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error recuperando un columna", 500, response);
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

		ServicioColumna servicioColumna = new ServicioColumna();
		ServicioProyecto servicioProyecto = new ServicioProyecto();
		Columna columna = new Columna();

		//recuperamos el proyecto de la columna
		Proyecto proyecto;
		try {
			proyecto = servicioProyecto.recuperarProyectoById(Integer.parseInt(request.getParameter("proyectoColumna")));
		} catch (NumberFormatException e1) {
			Utilidades.devolver("El id del proyecto de la columna debe ser un numero", 400, response);
			e1.printStackTrace();
			return;			
		} catch (ServiceException e1) {
			Utilidades.devolver("Proyecto de la columna no existe", 404, response);
			e1.printStackTrace();
			return;
		}

		//datos del columna
		try {
			columna.setNombre(request.getParameter("nombre"));
			columna.setProyecto(proyecto);
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un columna", 400, response);
			return;
		}

		//Insertar columna
		try {
			servicioColumna.insertarColumna(columna);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un columna", 500, response);
			return;
		}
		Utilidades.devolver(columna.toJSON(), 200, response);
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioColumna servicioColumna = new ServicioColumna();
		Columna columna;
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int columnaId = Integer.parseInt(splits[1]);
		try {
			columna = servicioColumna.recuperarColumnaById(columnaId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un columna", 500, response);
			return;
		}

		//datos del columna
		try {
			columna.setNombre(request.getParameter("nombre"));
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un columna", 400, response);
			return;
		}

		//modificar columna
		try {
			servicioColumna.modificarColumna(columna);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error modificando un columna", 500, response);
			return;
		}
		Utilidades.devolver("Columna modificado", 200, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioSesionIniciada = Utilidades.tieneSesionIniciada(request);

		if(usuarioSesionIniciada == null) {
			Utilidades.devolver("No has iniciado sesi�n", 401, response);
			return;
		}

		ServicioColumna servicioColumna = new ServicioColumna();
		Columna columna = new Columna();
		
		String urlInfo = request.getPathInfo();
		String[] splits = urlInfo == null ? new String[0] : urlInfo.split("/");

		if(splits.length > 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int columnaId = Integer.parseInt(splits[1]);
		try {
			columna = servicioColumna.recuperarColumnaById(columnaId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			Utilidades.devolver("Error recuperando un columna", 500, response);
			return;
		}

		//borrar columna
		try {
			if(servicioColumna.borrarColumna(columna)==0) {
				Utilidades.devolver("No hay columna que borrar", 400, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error borrando columna", 500, response);
			return;
		}
		Utilidades.devolver("Columna borrado", 200, response);
	}
}

