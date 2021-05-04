package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Sorvete;
import domain.Venda;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;
import util.DatabaseManager;

public class SorveteDAO {
	
	public boolean insertSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO SORVETES VALUES (?, ?, ?, ?)");
		ps.setInt(1, sorvete.codigo);
		ps.setString(2, sorvete.sabor);
		ps.setDouble(3, sorvete.valorUnidade);
		ps.setInt(4, sorvete.estoqueAtivo);
		
		int inserted = ps.executeUpdate();
		ps.close();
		
		return inserted > 0;
		
	}

	public boolean atualizarSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE SORVETES SET SABOR = ?, VALORUNIDADE = ?, ESTOQUEATIVO = ? WHERE CODIGO = ?");
		ps.setString(1, sorvete.sabor);
		ps.setDouble(2, sorvete.valorUnidade);
		ps.setInt(3, sorvete.estoqueAtivo);
		ps.setInt(4, sorvete.codigo);
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
		
	}
	
	public boolean atualizarEstoqueSorvete(int codigo, double estoquePosVenda) throws SQLException{
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE SORVETES SET ESTOQUEATIVO = ? WHERE CODIGO = ?");
		ps.setDouble(1, estoquePosVenda);
		ps.setInt(2, codigo);
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
	}

	public Sorvete findByPrimaryKey(int codigo) throws SQLException {
		Sorvete sorvete = new Sorvete();
		
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("SELECT * FROM SORVETES WHERE CODIGO = ?");
		ps.setInt(1, codigo);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			sorvete.codigo = rs.getInt(1);
			sorvete.sabor = rs.getString(2);
			sorvete.valorUnidade = rs.getDouble(3);
			sorvete.estoqueAtivo = rs.getInt(4);
		} else sorvete = null;
		
		rs.close();
		ps.close();
		return sorvete;
	}
	
	public boolean excluirSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("DELETE FROM SORVETES WHERE CODIGO = ?");
		ps.setInt(1, sorvete.codigo);

		int excluded = ps.executeUpdate();
		ps.close();
		
		return excluded > 0;	
	}
	
	public List<Sorvete> findAllSorvetes() throws SQLException {
		List<Sorvete> sorvetesList = new ArrayList<Sorvete>();
		
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT CODIGO, SABOR, VALORUNIDADE, ESTOQUEATIVO FROM SORVETES");
			
			while (rs.next()) {
				Sorvete sorvete = new Sorvete();
				sorvete.codigo = rs.getInt(1);
				sorvete.sabor = rs.getString(2);
				sorvete.valorUnidade = rs.getDouble(3);
				sorvete.estoqueAtivo = rs.getInt(4);
				sorvetesList.add(sorvete);
			}
			st.close();
			rs.close();
			return sorvetesList;
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			throw e;
		}
		
	}

	public static boolean atualizarSorveteEstoque() throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE SORVETES SET SABOR = ?, VALORUNIDADE = ?, ESTOQUEATIVO = ? WHERE CODIGO = ?");
		Sorvete sorvete = new Sorvete();
		Venda venda = new Venda();
		ps.setString(1, sorvete.sabor);
		ps.setDouble(2, sorvete.valorUnidade);
		ps.setDouble(3, venda.estoquePosVenda);
		ps.setInt(4, sorvete.codigo);
		     
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
	}

}