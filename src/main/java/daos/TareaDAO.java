package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.ITareaDAO;
import domain.Proyecto;
import domain.Usuario;
import domain.Columna;
import domain.Tarea;
import exceptions.DAOException;

public class TareaDAO implements ITareaDAO {


	private Connection con;

	public TareaDAO(Connection con) {
		this.con = con;
	}

	@SuppressWarnings("resource")
	public void insertarTarea(Tarea tarea)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;

		try {
			st = con.prepareStatement(DbQuery.getInsertarTarea());
			st.setInt(1, tarea.getID());
			st.setString(2, tarea.getTitulo());
			st.setString(3, tarea.getDescripcion());
			st.setInt(4, tarea.getProyecto().getID());
			st.setInt(5, tarea.getColumna().getID());
			st.setInt(6, tarea.getCreador().getID());
			st.setInt(7, tarea.getResponsable().getID());
			//rutina de erificacin de FK
			//proyecto
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, tarea.getProyecto().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto del usuario no existe");
			}  finally {
				Recursos.closeResultSet( rs);	
			}

			//columna
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarColumna());
				sti.setInt(1, tarea.getColumna().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" la columna de la tarea no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			//usuario (creador y responsable)
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, tarea.getCreador().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el creador de la tarea no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, tarea.getResponsable().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el responsable de la tarea no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("la tarea ya existe");
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
	public int modificarTarea(Tarea tarea)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado=0;

		try {
			st = con.prepareStatement(DbQuery.getModificarTarea());

			st.setInt(7, tarea.getID());
			st.setString(1, tarea.getTitulo());
			st.setString(2, tarea.getDescripcion());
			st.setInt(3, tarea.getProyecto().getID());
			st.setInt(4, tarea.getColumna().getID());
			st.setInt(5, tarea.getCreador().getID());
			st.setInt(6, tarea.getResponsable().getID());

			//rutina de erificacin de FK
			//proyecto
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, tarea.getProyecto().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto de la tarea no existe");
			}  finally {
				Recursos.closeResultSet( rs);	
			}

			//columna
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarColumna());
				sti.setInt(1, tarea.getColumna().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" la columna de la tarea no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			//usuario (creador y responsable)
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, tarea.getCreador().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el creador de la tarea no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			try{
				if(tarea.getResponsable() != null && tarea.getResponsable().getID() != 0){
					
				sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				sti.setInt(1, tarea.getResponsable().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el responsable de la tarea no existe");
				}
			}  finally {
				Recursos.closeResultSet(rs);	
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

	public Tarea recuperarTarea(Tarea tarea)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs=null;
		Tarea objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarTarea());
			st.setInt(1,tarea.getID() );
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Tarea(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Proyecto(rs.getInt(4)),
						new Columna(rs.getInt(5)),
						new Usuario(rs.getInt(6)),
						new Usuario(rs.getInt(7))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);			
		}
		return objeto;
	}

	public Tarea  recuperarTareaById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Tarea objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarTarea());
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Tarea(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Proyecto(rs.getInt(4)),
						new Columna(rs.getInt(5)),
						new Usuario(rs.getInt(6)),
						new Usuario(rs.getInt(7))); 
			}					
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);

		}
		return objeto;
	}
	public List<Tarea> recuperarTodosTarea(Columna columna)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Tarea> list = new ArrayList<Tarea>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosTarea());
			st.setInt(1, columna.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Tarea(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						new Proyecto(rs.getInt(4)),
						columna,
						new Usuario(rs.getInt(6)),
						new Usuario(rs.getInt(7)))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}


	public int borrarTarea(Tarea tarea) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarTarea());
			st.setInt(1, tarea.getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar tarea");

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

	public int borrarTareaById(int id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarTarea());
			st.setInt(1, id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar tarea");

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
