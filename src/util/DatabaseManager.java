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
	
//	criando um método estático que irá nos retornar uma conexão a partir do sqliteUtil
	public static Connection getConnection() throws SQLException {
//		O método con() irá retornar apenas uma conexão por instância do SQLiteUtil, então, se a conexão já existir, a mesma será retornada, se ela não existir uma nova será criada, e será usada nas próximas interações.		
		return sqliteUtil.con();
	}
	
// 	Criando as tabelas do banco de dados
	public static void loadTabelas() throws SQLException {
		Statement st = getConnection().createStatement();
		st.execute("CREATE TABLE IF NOT EXISTS SORVETES (CODIGO NUMERIC NOT NULL UNIQUE, SABOR VARCHAR UNIQUE, VALORUNIDADE NUMERIC, ESTOQUEATIVO NUMERIC, PRIMARY KEY(CODIGO))");
		st.execute("CREATE TABLE IF NOT EXISTS VENDAS (CODIGO NUMERIC NOT NULL UNIQUE, SABOR VARCHAR UNIQUE, VALORUNIDADE NUMERIC, VALORVENDA NUMERIC, VALORTOTAL NUMERIC, ESTOQUEVENDA NUMERIC, ESTOQUEATIVO NUMERIC, ESTOQUEVENDIDO NUMERIC, PRIMARY KEY(CODIGO))");
		//st.execute("DROP TABLE SORVETES");
		//st.execute("DROP TABLE VENDAS");
//      Lembre-se, que sempre ao término da utilização de um Statement ele deve ser fechado, através do método close(), para que sejam liberados os recursos por ele alocados.		
		st.close();
	}

}
