package programas;

import java.sql.Connection;
import java.util.List;

import bbdd.ConexionMySql;
import daos.ProyectoDAO;
import daos.UsuarioDAO;
import domain.Proyecto;
import domain.Usuario;
import exceptions.DAOException;
import exceptions.DomainException;
import exceptions.ServiceException;
import util.Teclado;

public class programaProyecto {
	
	
	public static void main(String[] args) {
		//Conexion
		Connection con;
		
		//Iniciacilamos
		Proyecto proyecto=null;
		Usuario usuario=null;
		List<Proyecto> listaProyecto = null;
		Teclado teclado=new Teclado();
		
		//llamo a los DAOs
		ProyectoDAO proyectoDAO=null;
		UsuarioDAO usuarioDAO=null;
		
		try {
			
			con=new ConexionMySql().getConexion();
			
			proyecto=new Proyecto();
			proyectoDAO=new ProyectoDAO(con); 
			usuarioDAO=new UsuarioDAO(con);
			
			//usuario.setID(0);
			proyecto.setNombre("proyect1");
			proyecto.setDescripcion("aaaaa");
			
			usuario= new Usuario();
			usuario=usuarioDAO.recuperarUsuarioById(1);
			System.out.println(usuario.toString());
			proyecto.setPropietario(usuario);
			  			 
			proyectoDAO.insertarProyecto(proyecto);
		       System.out.println("proyecto  " + proyecto.getID()+" insertado");

				// recuperar  proyecto *************************************
			       System.out.println("probando recuperar proyecto");
			       
			     
			       proyecto= proyectoDAO.recuperarProyectoById(teclado.leerEntero());
				  if (proyecto==null)
					  System.out.println("el proyecto no existe");	
				  else
				  System.out.println(proyecto.toString());
			
		} catch (ServiceException | DomainException |DAOException e) {
			if(e.getCause()==null){
				System.out.println(e.getMessage());//Error Lógico para usuario
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
