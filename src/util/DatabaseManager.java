package util;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;
import totalcross.sys.Vm;

public class DatabaseManager {

	
	public static SQLiteUtil sqliteUtil;
	
	static {
		try {
			sqliteUtil = new SQLiteUtil(Settings.appPath, "app.db");
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return sqliteUtil.con();
	}
	
	public static void loadTabelas() throws SQLException {
		Statement st = getConnection().createStatement();
		st.execute("CREATE TABLE IF NOT EXISTS SORVETES (CODIGO NUMERIC NOT NULL UNIQUE, SABOR VARCHAR UNIQUE, VALORUNIDADE NUMERIC, ESTOQUEATIVO NUMERIC)");
		st.execute("CREATE TABLE IF NOT EXISTS VENDAS (NUMERODOPEDIDO NUMERIC NOT NULL UNIQUE, CODIGO NUMERIC NOT NULL, SABOR VARCHAR, VALORUNIDADE NUMERIC, VALORVENDA NUMERIC, VALORTOTAL NUMERIC, ESTOQUEVENDA NUMERIC, ESTOQUEATIVO NUMERIC, ESTOQUEVENDIDO NUMERIC, PRIMARY KEY (NUMERODOPEDIDO))");
		//st.execute("DROP TABLE SORVETES");
		//st.execute("DROP TABLE VENDAS");
		st.close();
	}

}
