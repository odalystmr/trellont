package servlets;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;

import exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServicioUsuario;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServicioUsuario servicioUsuario = new ServicioUsuario();
		
		try {
			String token = servicioUsuario.iniciarSesion(request.getParameter("email"), request.getParameter("contra"));
			Utilidades.devolver(token , 200, response);
		}catch (ServiceException e) {
			Utilidades.devolver("Credenciales incorrectas. ", 400, response);
			return;
		}

	}

}
