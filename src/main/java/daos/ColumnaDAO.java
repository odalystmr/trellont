package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.DbQuery;
import recursos.Recursos;
import daos.interfaces.IColumnaDAO;
import domain.Proyecto;
import domain.Columna;
import exceptions.DAOException;

public class ColumnaDAO implements IColumnaDAO {


	private Connection con;

	public ColumnaDAO(Connection con) {
		this.con = con;
	}

	public void insertarColumna(Columna columna)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;

		try {
			st = con.prepareStatement(DbQuery.getInsertarColumna());
			st.setInt(1, columna.getID());
			st.setString(2, columna.getNombre());
			st.setInt(3, columna.getProyecto().getID());
			//rutina de erificacin de FK
			//usuario
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, columna.getProyecto().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto de la columna no existe");
			}  finally {
				Recursos.closeResultSet(rs);	
			}
			// ejecutamos el insert.			
			st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("columna ya existe");
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

	public int modificarColumna(Columna columna)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado=0;

		try {
			st = con.prepareStatement(DbQuery.getModificarColumna());

			st.setInt(1, columna.getID());
			st.setString(2, columna.getNombre());
			st.setInt(3, columna.getProyecto().getID());
			//rutina de erificacin de FK
			//usuario
			try{
				sti = con.prepareStatement(DbQuery.getRecuperarProyecto());
				sti.setInt(1, columna.getProyecto().getID());
				rs=sti.executeQuery();
				if(!rs.next())	
					throw new DAOException(" el proyecto de la columna no existe");
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

	public Columna recuperarColumna(Columna columna)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs=null;
		Columna objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarColumna());
			st.setInt(1,columna.getID() );
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Columna(rs.getInt(1),
						rs.getString(2),
						new Proyecto(rs.getInt(3))); 
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);			
		}
		return objeto;
	}

	public Columna  recuperarColumnaById(int id) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null ;
		Columna objeto=null;

		try {
			st = con.prepareStatement(DbQuery.getRecuperarColumna());
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				objeto=new Columna(rs.getInt(1),
						rs.getString(2),
						new Proyecto(rs.getInt(3))); 
			}					
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);

		}
		return objeto;
	}
	public List<Columna>  recuperarTodosColumna(Proyecto proyecto)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Columna> list = new ArrayList<Columna>();
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodosColumna());
			st.setInt(1, proyecto.getID());
			rs = st.executeQuery();
			while (rs.next()) {

				list.add(new Columna(rs.getInt(1),
						rs.getString(2),
						proyecto)); 
				}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {// cerramos cursores  y ResulSet
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;
	}


	public int borrarColumna(Columna columna) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarColumna());
			st.setInt(1, columna.getID());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar columna");

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

	public int borrarColumnaById(int id) throws DAOException {
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarColumna());
			st.setInt(1, id);
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar columna");

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
