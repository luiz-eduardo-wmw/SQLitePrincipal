package ui;


import java.sql.SQLException;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import util.DatabaseManager;

public class MainMenu extends MainWindow {

	private Button btSorvetes;
	
	private Button btVenda;
	
	private Button btSair;
	
	public MainMenu() throws SQLException {
		DatabaseManager.loadTabelas();
		btSorvetes = new Button("Sorvetes");
		
		btVenda = new Button("Vendas");
		
		btSair = new Button("Sair");
	}
	
	@Override
	public void initUI() {
		add(btSorvetes, LEFT + 10, TOP + 10, FILL - 10, PREFERRED);		
		add(btVenda, LEFT + 10, AFTER + 10, FILL - 10, PREFERRED);		
		add(btSair, LEFT + 10, BOTTOM - 10, FILL - 10, PREFERRED);		
	}
	
	@Override
	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btSorvetes) {
				btSorvetesClick();
			} else if (event.target == btVenda) {
				btVendaClick();
			}
			else if (event.target == btSair) {
				exit(AFTER);
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}

	private void btVendaClick() {
		ListarVendasWindow vendasWindow;
		try {
			vendasWindow = new ListarVendasWindow();
			vendasWindow.popup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void btSorvetesClick() {
		ListarSorvetesWindow sorvetesWindow;
		try {
			sorvetesWindow = new ListarSorvetesWindow();
			sorvetesWindow.popup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
