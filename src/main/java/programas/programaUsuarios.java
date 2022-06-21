package programas;

import java.sql.Connection;
import java.util.List;

import bbdd.ConexionMySql;
import daos.UsuarioDAO;
import domain.Usuario;
import exceptions.DAOException;
import exceptions.DomainException;
import exceptions.ServiceException;
import util.Teclado;

public class programaUsuarios {
	
	
	public static void main(String[] args) {
		//Conexion
		Connection con;
		//Iniciacilamos
		Usuario usuario=null;
		List<Usuario> listaUsuario = null;
		Teclado teclado=new Teclado();
		UsuarioDAO usuarioDAO=null;
		try {
			
			con=new ConexionMySql().getConexion();
			
			usuario=new Usuario();
			usuarioDAO=new UsuarioDAO(con); 
			
			//usuario.setID(0);
			usuario.setNombre("Odalys");
			usuario.setEmail("odalys1@gmail.es");
			usuario.setPassword("aa");
			  			 
			usuarioDAO.insertarUsuario(usuario);
		       System.out.println("usuario  " + usuario.getID()+" insertado");

				// recuperar  cliente *************************************
			       System.out.println("probando recuperar usuario");
			       
			     
			       usuario= usuarioDAO.recuperarUsuarioById(teclado.leerEntero());
				  if (usuario==null)
					  System.out.println("el ususario no existe");	
				  else
				  System.out.println(usuario.toString());
			
		} catch (ServiceException | DomainException |DAOException e) {
			if(e.getCause()==null){
				System.out.println(e.getMessage());//Error L�gico para usuario
			}else{
				e.printStackTrace();// para administrador
				System.out.println("error interno");//Error interno para usuario
			}
		}catch (Exception e){
			e.printStackTrace();// para administrador
			System.out.println("error interno no controlado");//Error interno para usuario
		}
	}

}
