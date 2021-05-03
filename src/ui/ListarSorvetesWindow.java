package ui;

import java.sql.SQLException;
import java.util.List;

import dao.SorveteDAO;
import domain.Sorvete;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PenEvent;

public class ListarSorvetesWindow extends Window {
	
	// SORVETES
	private Button btIncluir;
	private ScrollContainer listaSorvetes;
	private List<Sorvete> sorvetesList;
	private SorveteDAO sorveteDAO;
	//FIM DE SORVETES
	
	// VENDAS 
	private Button btListaPedidos;
	private Button btIncluirVendas;
	//FIM DE VENDAS

	// FUNCOES SISTEMAS
	private Button btVoltar;
	// FIM FUNCOES SISTEMAS
	
	public ListarSorvetesWindow() throws SQLException {
		// SORVETES
		listaSorvetes = new ScrollContainer();
		btIncluir = new Button("Incluir Sorvete");
		sorveteDAO = new SorveteDAO();
		sorvetesList = sorveteDAO.findAllSorvetes();
		// FIM DE SORVETES
		
		//VENDAS 
		btListaPedidos = new Button("Lista de Pedidos");
		btIncluirVendas = new Button("Nova Venda");
		//FIM DE VENDAS
		
		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		// FIM FUNCOES SISTEMAS
	}
	
	public void loadListSorvetes() throws SQLException {
		int index = 0;
		for (Sorvete sorvete : sorvetesList) {
			String[] dados = sorveteToArray(sorvete);
			Container sorveteContainer = new Container();
			sorveteContainer.appId = index ++;
			sorveteContainer.setBorderStyle(BORDER_SIMPLE);
			listaSorvetes.add(sorveteContainer, LEFT + 10, AFTER + 3, listaSorvetes.getWidth() -30, 50);
			for (int dadosIndex = 0; dadosIndex < 3; dadosIndex++) {
				int horizontalPosition = dadosIndex % 2 == 0 ? LEFT + 10 : RIGHT - 10;
				int verticalPosition = dadosIndex % 2 == 0 ? AFTER : SAME;
				sorveteContainer.add(new Label(dados[dadosIndex]), horizontalPosition, verticalPosition);
			}
		}
	}
	
	private String [] sorveteToArray(Sorvete sorvete) {
		String[] dadosArray = new String[3];
		//dadosArray[0] = String.valueOf(sorvete.codigo);
		dadosArray[0] = sorvete.sabor;
		dadosArray[1] = String.valueOf(sorvete.valorUnidade);
		dadosArray[2] = String.valueOf(sorvete.estoqueAtivo);
		return dadosArray;
	}
	
	private void reloadListSorvetes() throws SQLException  {
		sorvetesList = sorveteDAO.findAllSorvetes();
		listaSorvetes = new ScrollContainer();
		removeAll();
		montaTela();
		reposition();
	}
	
	@Override
	public void initUI() {
		montaTela();
	}
	
	public void montaTela() {
		add(listaSorvetes, LEFT, TOP, FILL, getScrollContainerSizeSorvetes());
		try {
			loadListSorvetes();
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
		
		// FUNCOES SISTEMAS
		add(btVoltar, RIGHT - 30, BOTTOM - 10);
		// FIM FUNCOES SISTEMAS

		// VENDAS
		add(btListaPedidos, LEFT + 30, BOTTOM - 10);
		add(btIncluirVendas, AFTER + 10, BOTTOM - 10);		
		// FIM DE VENDAS		
		
		// SORVETES
		add(btIncluir, AFTER + 10, BOTTOM - 10);
		// FIM DE SORVETES
		
	}
	
	private int getScrollContainerSizeSorvetes() {
		int size = sorvetesList.size() * 50 + (sorvetesList.size() *3) + 10;
		size = size > Settings.screenHeight ? Settings.screenHeight - 10 : size;
		return size;		
	}
	
	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}
	
	@Override
	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
		if (event.target == btIncluir) {
			IncluirSorvetesWindow sorvetesWindow = new IncluirSorvetesWindow();
			sorvetesWindow.popup();
			try {
				reloadListSorvetes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} /*else if (event.target == btListaPedidos) {
			System.out.println("VOCÊ CLICOU EM LISTA DE PEDIDOS");
			ListarVendasWindow vendasWindow;
			try {
				vendasWindow = new ListarVendasWindow();
				vendasWindow.popup();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/ else if (event.target == btVoltar) {
			this.unpop();
		}
		break;
		case PenEvent.PEN_DOWN:
			if (event.target instanceof Container && !(event.target instanceof Window)) {
				Container c = (Container) event.target;
				Sorvete sorvete = sorvetesList.get(c.appId);
				if (sorvete == null) return;
				IncluirSorvetesWindow sorvetesWindow = new IncluirSorvetesWindow(sorvete);
				sorvetesWindow.popup();
				try {
					reloadListSorvetes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;		
		}
		super.onEvent(event);
	}
	
}
