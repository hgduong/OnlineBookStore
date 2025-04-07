package dao.dbcontext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataBase {
	abstract Connection createConnection();

	abstract void closeConnection(Connection connection) throws SQLException;

}
