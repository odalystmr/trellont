package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import domain.Usuario;

import exceptions.DomainException;
import exceptions.ServiceException;

import servicios.ServicioUsuario;

import servlets.Utilidades;


/**
 * Servlet implementation class ServletSignIn
 */
@WebServlet("/signin")
public class ServletSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		Usuario usuario = new Usuario();

		//datos del usuario
		try {
			usuario.setNombre(request.getParameter("nombre"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setPassword(request.getParameter("contra"));
		}catch (DomainException e) {
			e.printStackTrace();
			Utilidades.devolver("Error poniendo los datos de un usuario", 400, response); //Error Cliente
			return;
		}

		//Insertar usuario
		try {
			servicioUsuario.insertarUsuario(usuario);
		} catch (ServiceException e) {
			e.printStackTrace();
			Utilidades.devolver("Error creando un usuario " + e.getMessage(), 500, response); //Error Servidor
			return;
		}
		Utilidades.devolver("Usuario creado", 201, response); // >200 = ok -- 201 = creado
	}
		
	

}
