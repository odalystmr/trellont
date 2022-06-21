package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServicioUsuario;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Usuario;

public class Utilidades {
	public static Usuario tieneSesionIniciada(HttpServletRequest request) {
		// Proceso para comprobar si el usuario tiene sesi�n iniciada
		String token = Utilidades.getTokenCookie(request);

		ServicioUsuario servicioUsuario = new ServicioUsuario();

		return servicioUsuario.tieneSesionIniciada(token);
	}

	public static String getTokenCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if(cookies == null) 
			return "";
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
		
			if(cookie.getName().equals("token") ) {
				return cookie.getValue();
			}
		}

		return "";
	}

	public static void devolver(String texto, int codigo, HttpServletResponse response) throws IOException {
		response.setContentType( "text/html" ); // setting MIME type
		response.setStatus(codigo);
		PrintWriter out = response.getWriter();
		out.println(texto);
		out.close();
	}
}
