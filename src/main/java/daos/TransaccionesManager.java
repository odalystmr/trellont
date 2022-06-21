package daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import bbdd.ConexionMySql;
import exceptions.DAOException;

public class TransaccionesManager {

	private static final String DB_ERR = "Error de la base de datos";
	//	private static final String DB_CON_ERR = "Error al conectar con la base de datos";

	private Connection con;

	// para  octener la conexion del pool de conexiones, ver WEB.xml
	/*	  public TransaccionesManager() throws DAOException {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/PoolDeConexiones");
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} catch (NamingException e) {
			throw new DAOException(DB_CON_ERR, e);
		}
	}
	 */
	// la conexion la establezco con la clase ConexionOracle
	public TransaccionesManager() throws DAOException {

		con=new ConexionMySql("jdbc:mysql://localhost:3306/proyecto",
				"odalystmr","1234").getConexion();
		/*	
		con=new ConexionOracle("jdbc:oracle:thin:@b07profesor:1521:cr7",
				"angel","numancia").getConexion();
		 */
		try {	
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DAOException( DB_ERR ,e);	
		}
	}	

	public void closeCommit()throws DAOException  {
		try {
			if(con!=null){
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			throw new DAOException (DB_ERR, e);
		}
	}
	public void commit()throws DAOException  {
		try {
			if(con!=null){
				con.commit();
			}
		} catch (SQLException e) {
			throw new DAOException (DB_ERR, e);
		}
	}
	public void closeRollback() throws DAOException {
		try {
			if(con!=null){
				con.rollback();
				con.close();
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
	}
	public void rollback() throws DAOException {
		try {
			if(con!=null){
				con.rollback();
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
	}
	public void close() throws DAOException {
		try {
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}

	}
	public Savepoint savepoint()throws DAOException{
		Savepoint s = null;
		try{
			if(con != null){
				s = con.setSavepoint();
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
		return s;
	}
	public void deshacerHastaSavepoint(Savepoint s)throws DAOException{
		try{
			if(con != null){
				con.rollback(s);
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
	}
	public Connection getConexion() {
		return con;
	}

	public UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO(con);
	}
	public ProyectoDAO getProyectoDAO() {
		return new ProyectoDAO(con);
	}
	public Proyecto_UsuarioDAO getProyecto_UsuarioDAO() {
		return new Proyecto_UsuarioDAO(con);
	}
	public ColumnaDAO getColumnaDAO() {
		return new ColumnaDAO(con);
	}	
	public TareaDAO getTareaDAO() {
		return new TareaDAO(con);
	}
	public ComentarioDAO getComentarioDAO() {
		return new ComentarioDAO(con);
	}




}
