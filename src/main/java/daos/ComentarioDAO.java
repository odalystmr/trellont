package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.IComentarioDAO;
import domain.Usuario;
import domain.Tarea;
import domain.Comentario;
import exceptions.DAOException;

public class ComentarioDAO implements IComentarioDAO {


	private Connection con;

	public ComentarioDAO(Connection con) {
		this.con = con;
	}

	@SuppressWarnings("resource")
	public void insertarComentario(Comentario comentario)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;

		try {
			st = con.prepareStatement(DbQuery.getInsertarComentario());
			st.setInt(1, comentario.getID());
			st.setString(2, comentario.getComentario());
			st.setInt(3, comentario.getTarea().getID());
			st.setInt(4, comentario.getUsuario().getID());
			//rutina de erificacin de FK
			//tarea
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarTarea());
				sti.setInt(1, comentario.getTarea().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("la tarea del comentario no existe ");
			}  finally {
				Recursos.closeResultSet( rs);	
			}
			//usuario   
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, comentario.getUsuario().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("el usuario del comentario no existe");
			} finally {
				Recursos.closeResultSet( rs);	
			}
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("comentario ya existe");
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
	public int modificarComentario(Comentario comentario)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado=0;

		try {
			st = con.prepareStatement(DbQuery.getModificarComentario());

			st.setInt(1, comentario.getID());
			st.setString(2, comentario.getComentario());
			st.setInt(3, comentario.getTarea().getID());
			st.setInt(4, comentario.getUsuario().getID());
			//rutina de erificacin de FK
			//tarea
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarTarea());
				sti.setInt(1, comentario.getTarea().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("la tarea del comentario no existe ");
			}  finally {
				Recursos.closeResultSet( rs);	
			}
			//usuario   
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, comentario.getUsuario().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException("el usuario del comentario no existe");
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

	public Comentario recuperarComentario(Comentario comentario)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs=null;
		Comentario objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarComentario());
			st.setInt(1,comentario.getID() );
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Comentario(rs.getInt(1),
						rs.getString(2),
						new Tarea(rs.getInt(3)),
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

	public Comentario  recuperarComentarioById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Comentario objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarComentario());
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Comentario(rs.getInt(1),
						rs.getString(2),
						new Tarea(rs.getInt(3)),
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
	public List<Comentario>  recuperarTodosComentario(Tarea tarea)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Comentario> list = new ArrayList<Comentario>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosComentario());
			st.setInt(1, tarea.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Comentario(rs.getInt(1),
						rs.getString(2),
						tarea,
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


	public int borrarComentario(Comentario comentario) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarComentario());
			st.setInt(1, comentario.getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar comentario");

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

	public int borrarComentarioById(int id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarComentario());
			st.setInt(1, id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar comentario");

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
