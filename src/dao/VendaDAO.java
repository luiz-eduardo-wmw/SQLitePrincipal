package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Venda;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;
import util.DatabaseManager;

public class VendaDAO {

	public static boolean insertVenda(Venda venda) throws SQLException {
			PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO VENDAS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, venda.numeroDoPedido); //PRIMARY KEY
			ps.setInt(2, venda.codigo); 
			ps.setString(3, venda.sabor);
			ps.setDouble(4, venda.valorUnidade);
			ps.setDouble(5, venda.valorVenda);
			ps.setDouble(6, venda.valorTotal);
			ps.setDouble(7, venda.estoqueVenda);
			ps.setDouble(8, venda.estoqueAtivo);
			ps.setDouble(9, venda.estoqueVendido);
			
			int inserted = ps.executeUpdate();
			ps.close();
			
			return inserted > 0;
			
		}
		

	public static boolean atualizarVenda(Venda venda) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE VENDAS SET SABOR = ?, VALORUNIDADE = ?, VALORVENDA = ?, ESTOQUEVENDA = ?, ESTOQUEATIVO = ? WHERE NUMERODOPEDIDO = ?");
		ps.setString(1, venda.sabor);
		ps.setDouble(2, venda.valorUnidade);
		ps.setDouble(3, venda.valorVenda);
		ps.setDouble(4, venda.estoqueVenda);
		ps.setInt(6, venda.numeroDoPedido); //PRIMARY KEY
		
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
		
	}

	public static boolean excluirVenda(Venda venda) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("DELETE FROM VENDAS WHERE NUMERODOPEDIDO = ?");
		ps.setInt(1, venda.numeroDoPedido);

		int excluded = ps.executeUpdate();
		ps.close();
		
		return excluded > 0;	
	}
	
	public List<Venda> findAllVendas() throws SQLException {
		List<Venda> vendasList = new ArrayList<Venda>();
		
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM VENDAS");
			
			while (rs.next()) {
				Venda venda = new Venda();
				venda.numeroDoPedido = rs.getInt(1);
				venda.codigo = rs.getInt(2);
				venda.sabor = rs.getString(3);
				venda.valorUnidade = rs.getDouble(4);
				venda.valorVenda = rs.getDouble(5);
				venda.valorTotal = rs.getDouble(6);
				venda.estoqueVenda = rs.getDouble(7);
				venda.estoqueAtivo = rs.getDouble(8);
				venda.estoqueVendido = rs.getDouble(9);

				vendasList.add(venda);
			}
			st.close();
			rs.close();
			return vendasList;
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			throw e;
		}
		
	}

}


