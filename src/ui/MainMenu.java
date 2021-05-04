package ui;


import java.sql.SQLException;

import totalcross.io.IOException;
import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;
import util.DatabaseManager;

public class MainMenu extends MainWindow {

	private Button btSorvetes;
	
	private Button btVenda;
	
	private Button btSair;
	
	private Button btLogo;
	
	public MainMenu() throws SQLException, ImageException, IOException {
		DatabaseManager.loadTabelas();
		btSorvetes = new Button("Sorvetes");
		
		btVenda = new Button("Vendas");
		
		btSair = new Button("Sair");
		
		btLogo = new Button (new Image("/resources/logoWMW 120X60.png"), Button.BORDER_NONE);
	}
	
	@Override
	public void initUI() {
		add(btLogo, CENTER, TOP + 10);
		add(btSorvetes, LEFT + 10, AFTER + 10, FILL - 10, PREFERRED);		
		add(btVenda, LEFT + 10, AFTER + 10, FILL - 10, PREFERRED);		
		add(btSair, LEFT + 10, BOTTOM - 10, FILL - 10, PREFERRED);
	}
	
	@Override
	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btSorvetes) {
				try {
					btSorvetesClick();
				} catch (ImageException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (event.target == btVenda) {
				try {
					btVendaClick();
				} catch (SQLException | ImageException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

	private void btVendaClick() throws ImageException, IOException, SQLException {
		ListarVendasWindow vendasWindow;
		vendasWindow = new ListarVendasWindow();
		vendasWindow.popup();
	}

	private void btSorvetesClick() throws ImageException, IOException {
		ListarSorvetesWindow sorvetesWindow;
		try {
			sorvetesWindow = new ListarSorvetesWindow();
			sorvetesWindow.popup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
