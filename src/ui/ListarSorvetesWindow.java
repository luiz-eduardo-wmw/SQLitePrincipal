package ui;

import java.sql.SQLException;
import java.util.List;

import dao.SorveteDAO;
import domain.Sorvete;
import totalcross.io.IOException;
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
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class ListarSorvetesWindow extends Window {

	// SORVETES
	private Button btIncluir;
	private ScrollContainer listaSorvetes;
	private List<Sorvete> sorvetesList;
	private SorveteDAO sorveteDAO;
	// FIM DE SORVETES

	// VENDAS
	private Button btListaPedidos;
	private Button btIncluirNovaVenda;
	// FIM DE VENDAS

	// FUNCOES SISTEMAS
	private Button btVoltar;
	private Button btLogo;
	// FIM FUNCOES SISTEMAS

	public ListarSorvetesWindow() throws SQLException, ImageException, IOException {
		// SORVETES
		listaSorvetes = new ScrollContainer();
		btIncluir = new Button("Incluir Sorvete");
		sorveteDAO = new SorveteDAO();
		sorvetesList = sorveteDAO.findAllSorvetes();
		// FIM DE SORVETES

		// VENDAS
		btListaPedidos = new Button("Lista de Pedidos");
		btIncluirNovaVenda = new Button("Nova Venda");
		// FIM DE VENDAS

		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM FUNCOES SISTEMAS
	}

	public void loadListSorvetes() throws SQLException {
		int index = 0;
		for (Sorvete sorvete : sorvetesList) {
			String[] dados = sorveteToArray(sorvete);
			Container sorveteContainer = new Container();
			sorveteContainer.appId = index++;
			sorveteContainer.setBorderStyle(BORDER_SIMPLE);
			listaSorvetes.add(sorveteContainer, LEFT + 10, AFTER + 3, listaSorvetes.getWidth() - 30, 50);
			for (int dadosIndex = 0; dadosIndex < 4; dadosIndex++) {
				int horizontalPosition = dadosIndex % 2 == 0 ? LEFT + 10 : RIGHT - 10;
				int verticalPosition = dadosIndex % 2 == 0 ? AFTER : SAME;
				sorveteContainer.add(new Label(dados[dadosIndex]), horizontalPosition, verticalPosition);
			}
		}
	}

	private String[] sorveteToArray(Sorvete sorvete) {
		String[] dadosArray = new String[4];
		dadosArray[0] = String.valueOf(sorvete.codigo);
		dadosArray[2] = sorvete.sabor;
		dadosArray[1] = "R$" + String.valueOf(sorvete.valorUnidade);
		dadosArray[3] = String.valueOf(sorvete.estoqueAtivo);
		return dadosArray;
	}

	private void reloadListSorvetes() throws SQLException {
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
		add(btLogo, LEFT + 10, TOP);
		add(new Label("RELATÓRIO DE ESTOQUE"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		add(listaSorvetes, LEFT, AFTER + 10, FILL, getScrollContainerSizeSorvetes());
		try {
			loadListSorvetes();
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}

		// FUNCOES SISTEMAS
		add(btVoltar, RIGHT - 10, BOTTOM - 10);
		// FIM FUNCOES SISTEMAS

		// VENDAS
		add(btListaPedidos, LEFT + 10, BOTTOM - 10);
		add(btIncluirNovaVenda, AFTER + 10, BOTTOM - 10);
		// FIM DE VENDAS

		// SORVETES
		add(btIncluir, AFTER + 10, BOTTOM - 10);
		// FIM DE SORVETES

	}

	private int getScrollContainerSizeSorvetes() {
		int size = sorvetesList.size() * 50 + (sorvetesList.size() * 3) + 10;
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
				IncluirSorvetesWindow sorvetesWindow;
				try {
					sorvetesWindow = new IncluirSorvetesWindow();
					sorvetesWindow.popup();
				} catch (ImageException | IOException e1) {
					e1.printStackTrace();
				}
				try {
					reloadListSorvetes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (event.target == btListaPedidos) {
				ListarVendasWindow vendasWindow;
				try {
					vendasWindow = new ListarVendasWindow();
					vendasWindow.popup();
				} catch (SQLException | ImageException | IOException e) {
					e.printStackTrace();
				}
			} else if (event.target == btVoltar) {
				this.unpop();
			} else if (event.target == btIncluirNovaVenda) {
				btIncluirNovaVendaClick();
			}
			break;
		case PenEvent.PEN_DOWN:
			if (event.target instanceof Container && !(event.target instanceof Window)) {
				Container c = (Container) event.target;
				Sorvete sorvete = sorvetesList.get(c.appId);
				if (sorvete == null)
					return;
				IncluirSorvetesWindow sorvetesWindow;
				try {
					sorvetesWindow = new IncluirSorvetesWindow(sorvete);
					sorvetesWindow.popup();
				} catch (ImageException | IOException e1) {
					e1.printStackTrace();
				}
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

	private void btIncluirNovaVendaClick() {
		IncluirVendasWindow vendasWindow;
		try {
			vendasWindow = new IncluirVendasWindow();
			vendasWindow.popup();
		} catch (ImageException | IOException e) {
			e.printStackTrace();
		}
	}
}
