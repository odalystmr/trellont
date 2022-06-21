package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.IProyectoDAO;
import domain.Proyecto;
import domain.Usuario;
import exceptions.DAOException;

public class ProyectoDAO implements IProyectoDAO {


	private Connection con;

	public ProyectoDAO(Connection con) {
		this.con = con;
	}

	public void insertarProyecto(Proyecto proyecto)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;

		try {
			st = con.prepareStatement(DbQuery.getInsertarProyecto());
			st.setInt(1, proyecto.getID());
			st.setString(2, proyecto.getNombre());
			st.setString(3, proyecto.getDescripcion());
			st.setInt(4, proyecto.getPropietario().getID());
			//rutina de erificacin de FK
			//usuario
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, proyecto.getPropietario().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el propietario del proyecto no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("proyecto ya existe");
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

	public int modificarProyecto(Proyecto proyecto)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado=0;

		try {
			st = con.prepareStatement(DbQuery.getModificarProyecto());

			st.setInt(3, proyecto.getID());
			st.setString(1, proyecto.getNombre());
			st.setString(2, proyecto.getDescripcion());

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

	public void relacionarParticipante(Proyecto proyecto, Usuario participante)throws DAOException{
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement(DbQuery.getRelacionarParticipante());

			st.setInt(1, proyecto.getID());
			st.setInt(2, participante.getID());

			st.executeUpdate();
		} catch (SQLException e) {
			if  (e.getErrorCode() >= 20000 && e.getErrorCode() <= 20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
				throw new DAOException(cadena1);
			}

			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores 
			Recursos.closePreparedStatement(st);
		}
	}

	public Proyecto recuperarProyecto(Proyecto proyecto)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs=null;
		Proyecto objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarProyecto());
			st.setInt(1,proyecto.getID() );
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Proyecto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Usuario(rs.getInt(4))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);			
		}
		return objeto;
	}
	public Proyecto recuperarProyectoById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Proyecto objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarProyecto());
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Proyecto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Usuario(rs.getInt(4))); 
			}					
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);

		}
		return objeto;
	}
	public List<Proyecto> recuperarTodosProyecto()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Proyecto> list = new ArrayList<Proyecto>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosProyecto());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Proyecto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Usuario(rs.getInt(4)))); 
				}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}
	public List<Proyecto> recuperarTodosProyectoDeUsuario(Usuario usuario)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Proyecto> list = new ArrayList<Proyecto>();
		
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosProyectoDeUsuario());
			st.setInt(1, usuario.getID());
			st.setInt(2, usuario.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Proyecto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						usuario )); 
				}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}
	
	public List<Usuario> recuperarTodosUsuarioDeProyecto(Proyecto proyecto)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Usuario> list = new ArrayList<Usuario>();
		
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosUsuarioDeProyecto());
			st.setInt(1, proyecto.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Usuario(rs.getInt(1),
						rs.getString(2),
						rs.getString(3), null, null)); 
				}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}
	public int borrarProyecto(Proyecto proyecto) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarProyecto());
			st.setInt(1, proyecto.getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar proyecto");

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

	public int borrarProyectoById(int id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarProyecto());
			st.setInt(1, id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar proyecto");

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
