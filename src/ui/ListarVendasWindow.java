package ui;

import java.sql.SQLException;
import java.util.List;

import dao.SorveteDAO;
import dao.VendaDAO;
import domain.Sorvete;
import domain.Venda;
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

public class ListarVendasWindow extends Window {

	// OS BOT�ES DE LAYOUT
	private Button btVoltar;
	private Button btLogo;
	// FIM

	// VENDAS
	private Button btIncluirNovaVenda;
	private ScrollContainer listaVendas;
	private List<Venda> vendasList;
	private VendaDAO vendaDAO;
	// FIM

	public ListarVendasWindow() throws ImageException, IOException, SQLException {

		// VENDAS
		listaVendas = new ScrollContainer();
		btIncluirNovaVenda = new Button("Incluir Nova Venda");
		vendaDAO = new VendaDAO();
		vendasList = vendaDAO.findAllVendas();
		// FIM

		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	public void loadListVendas() throws SQLException {
		int index = 0;
		for (Venda venda : vendasList) {
			String[] dados = vendaToArray(venda);
			Container vendaContainer = new Container();
			vendaContainer.appId = index++;
			vendaContainer.setBorderStyle(BORDER_SIMPLE);
			listaVendas.add(vendaContainer, LEFT + 10, AFTER + 3, listaVendas.getWidth() - 30, 50);
			for (int dadosIndex = 0; dadosIndex < 4; dadosIndex++) {
				int horizontalPosition = dadosIndex % 2 == 0 ? LEFT + 10 : RIGHT - 10;
				int verticalPosition = dadosIndex % 2 == 0 ? AFTER : SAME;
				vendaContainer.add(new Label(dados[dadosIndex]), horizontalPosition, verticalPosition);
			}
		}
	}

	private String[] vendaToArray(Venda venda) {
		String[] dadosArray = new String[4];
<<<<<<< HEAD
		dadosArray[0] = "Pedido n� " + String.valueOf(venda.numeroDoPedido);
=======
		dadosArray[0] = String.valueOf(venda.codigo);
>>>>>>> 36b58261fffc9eb09e1694b8f434db882a9ea01f
		dadosArray[1] = "R$" + String.valueOf(venda.valorVenda);
		dadosArray[2] = venda.sabor;
		dadosArray[3] = String.valueOf(venda.estoqueVenda);
		return dadosArray;
	}

	private void reloadListVendas() throws SQLException {
		vendasList = vendaDAO.findAllVendas();
		listaVendas = new ScrollContainer();
		removeAll();
		montaTela();
		reposition();
	}

	@Override
	public void initUI() {
		montaTela();
		
	}

	public void montaTela() {
		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("RELATORIO DE VENDAS"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM
		add(listaVendas, LEFT, AFTER + 10, FILL, getScrollContainerSizeSorvetes());
		try {
			loadListVendas();
			/*
			 * add(btLogo, CENTER + 10, TOP); add(new Label("RELATORIO DE VENDAS"), AFTER +
			 * 65, SAME + 10, FILL - 10, PREFERRED);
			 */
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}

		// FUNCOES SISTEMAS
		add(btVoltar, RIGHT - 10, BOTTOM - 10);
		// FIM FUNCOES SISTEMAS

		// VENDAS
		add(btIncluirNovaVenda, LEFT + 10, BOTTOM - 10);		
		// FIM DE VENDAS
	}

	private int getScrollContainerSizeSorvetes() {
		int size = vendasList.size() * 50 + (vendasList.size() * 3) + 10;
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
			if (event.target == btVoltar) {
				this.unpop();
			} else if (event.target == btIncluirNovaVenda) {
				btIncluirNovaVendaClick();
			}
			break;
		case PenEvent.PEN_DOWN:
			if (event.target instanceof Container && !(event.target instanceof Window)) {
				Container c = (Container) event.target;
				Venda venda = vendasList.get(c.appId);
				if (venda == null)
					return;
				IncluirVendasWindow vendasWindow;
				try {
					vendasWindow = new IncluirVendasWindow(venda);
					vendasWindow.popup();
<<<<<<< HEAD
				} catch (SQLException | ImageException | IOException e) {
=======
				} catch (ImageException | IOException e) {
>>>>>>> 36b58261fffc9eb09e1694b8f434db882a9ea01f
					e.printStackTrace();
				}
				try {
					reloadListVendas();
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
