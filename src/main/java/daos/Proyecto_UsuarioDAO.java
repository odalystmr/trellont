package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.IProyecto_UsuarioDAO;
import domain.Proyecto;
import domain.Usuario;
import domain.Proyecto_Usuario;
import exceptions.DAOException;

public class Proyecto_UsuarioDAO implements IProyecto_UsuarioDAO {


	private Connection con;

	public Proyecto_UsuarioDAO(Connection con) {
		this.con = con;
	}

	@SuppressWarnings("resource")
	public void insertarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;

		try {
			st = con.prepareStatement(DbQuery.getInsertarProyecto_Usuario());
			st.setInt(1, proyecto_usuario.getProyecto_id().getID());
			st.setInt(2, proyecto_usuario.getUsuario_id().getID());
			//rutina de erificacin de FK
			//proyecto
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, proyecto_usuario.getProyecto_id().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto de proyecto_usuario no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			//usuario
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, proyecto_usuario.getUsuario_id().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("el usuario del proyecto_usuario no existe");
			} finally {
				Recursos.closeResultSet( rs);	
			}
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("proyecto_usuario ya existe");
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
				throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {// cerramos cursores 
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);	
		}	
	}

	@SuppressWarnings("resource")
	public int modificarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado=0;

		try {
			st = con.prepareStatement(DbQuery.getModificarProyecto_Usuario());

			st.setInt(1, proyecto_usuario.getProyecto_id().getID());
			st.setInt(2, proyecto_usuario.getUsuario_id().getID());
			//rutina de erificacin de FK
			//proyecto
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, proyecto_usuario.getProyecto_id().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto de proyecto_usuario no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			//usuario
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, proyecto_usuario.getUsuario_id().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("el usuario del proyecto_usuario no existe");
			} finally {
				Recursos.closeResultSet( rs);	
			}
			modificado=st.executeUpdate();
		} catch (SQLException e) {
			if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
				throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {// cerramos cursores 
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);	
			Recursos.closeResultSet(rs);
		}
		return modificado;	
	}

	public Proyecto_Usuario recuperarProyecto_Usuario(Proyecto_Usuario proyecto_usuario)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs=null;
		Proyecto_Usuario objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarProyecto_Usuario());
			st.setInt(1,proyecto_usuario.getProyecto_id().getID() );
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Proyecto_Usuario(new Proyecto(rs.getInt(1)),
						new Usuario(rs.getInt(2))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);			
		}
		return objeto;
	}

	public Proyecto_Usuario  recuperarProyecto_UsuarioById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Proyecto_Usuario objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarProyecto_Usuario());
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Proyecto_Usuario(new Proyecto(rs.getInt(1)),
						new Usuario(rs.getInt(2))); 
			}					
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);

		}
		return objeto;
	}
	public List<Proyecto_Usuario>  recuperarTodosProyecto_Usuario(Proyecto proyecto)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Proyecto_Usuario> list = new ArrayList<Proyecto_Usuario>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosProyecto_Usuario());
			st.setInt(1, proyecto.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Proyecto_Usuario(proyecto,
						new Usuario(rs.getInt(2)))); 
				}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}


	public int borrarProyecto_Usuario(Proyecto_Usuario proyecto_usuario) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarProyecto_Usuario());
			st.setInt(1, proyecto_usuario.getProyecto_id().getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar proyecto_usuario");

			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
				throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closePreparedStatement(st);
		}
		return borrado;
	}

	public int borrarProyecto_UsuarioById(int proyecto_id, int usuario_id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarProyecto_Usuario());
			st.setInt(1, proyecto_id);
			st.setInt(2, usuario_id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar proyecto_usuario");

			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
				throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closePreparedStatement(st);
		}
		return borrado;		
	}

	

}
