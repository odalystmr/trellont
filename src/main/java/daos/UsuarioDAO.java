package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.IUsuarioDAO;
import domain.Usuario;
import exceptions.DAOException;

public class UsuarioDAO implements IUsuarioDAO {
	
	
	private Connection con;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	public void insertarUsuario(Usuario usuario)throws DAOException{
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement(DbQuery.getInsertarUsuario());
			
			st.setString(1, usuario.getNombre());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getPassword());
			
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Usuario ya existe");
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
			    throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {// cerramos cursores 
			Recursos.closePreparedStatement(st);
		}	
	}
	
	public int modificarUsuario(Usuario usuario)throws DAOException{
		PreparedStatement st = null;
		int modificado=0;
		
		try {
			st = con.prepareStatement(DbQuery.getModificarUsuario());
			
			st.setInt(5, usuario.getID());
			st.setString(1, usuario.getNombre());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getPassword());
			st.setString(4, usuario.getToken());
			
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
		}
		return modificado;	
	}
	
	public Usuario recuperarUsuario(Usuario usuario)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Usuario objeto=null;
		
			try {
				st = con.prepareStatement(DbQuery.getRecuperarUsuario());
				st.setInt(1,usuario.getID() );
				rs=st.executeQuery();
				if (rs.next()){
					objeto=new Usuario(rs.getInt(1),
							  rs.getString(2),
							  rs.getString(3),
							  rs.getString(4),
							  rs.getString(5)); 
				}
			} catch (SQLException e) {
				throw new DAOException(DB_ERR, e);
			} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);			
		}
		return objeto;
	}
	
	public Usuario recuperarUsuarioById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Usuario objeto=null;
		
			try {
				st = con.prepareStatement(DbQuery.getRecuperarUsuario());
				st.setInt(1, id);
				rs=st.executeQuery();
				if (rs.next()){
					objeto=new Usuario(rs.getInt(1),
							 rs.getString(2),
							  rs.getString(3),
							  rs.getString(4),
							  rs.getString(5)); 
				}					
			} catch (SQLException e) {
				throw new DAOException(DB_ERR, e);
			} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			
		}
		return objeto;
	}
	public Usuario recuperarUsuarioByEmail(String email) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Usuario objeto=null;
		
			try {
				st = con.prepareStatement(DbQuery.getRecuperarUsuarioEmail());
				st.setString(1, email);
				rs=st.executeQuery();
				if (rs.next()){
					objeto=new Usuario(rs.getInt(1),
							 rs.getString(2),
							  rs.getString(3),
							  rs.getString(4),
							  rs.getString(5)); 
				}					
			} catch (SQLException e) {
				throw new DAOException(DB_ERR, e);
			} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			
		}
		return objeto;
	}
	public Usuario recuperarUsuarioByToken(String token) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Usuario objeto=null;
		
			try {
				st = con.prepareStatement(DbQuery.getRecuperarUsuarioToken());
				st.setString(1, token);
				rs=st.executeQuery();
				if (rs.next()){
					objeto=new Usuario(rs.getInt(1),
							 rs.getString(2),
							  rs.getString(3),
							  rs.getString(4),
							  rs.getString(5)
							  ); 
				}					
			} catch (SQLException e) {
				throw new DAOException(DB_ERR, e);
			} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			
		}
		return objeto;
	}
	public List<Usuario> recuperarTodosUsuario()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Usuario> list = new ArrayList<Usuario>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosUsuario());
			rs = st.executeQuery();
			while (rs.next()) {
				
				list.add(new Usuario(rs.getInt(1),
						 rs.getString(2),
						  rs.getString(3),
						  rs.getString(4),
						  rs.getString(5))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}

	
	public int borrarUsuario(Usuario usuario) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarUsuario());
			st.setInt(1, usuario.getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar usuario");
				
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

	public int borrarUsuarioById(int id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarUsuario());
			st.setInt(1, id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar usuario");
				
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
