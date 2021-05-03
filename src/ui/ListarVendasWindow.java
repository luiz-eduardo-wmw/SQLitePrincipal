package ui;

import totalcross.io.IOException;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class ListarVendasWindow extends Window {

	// OS BOTÕES DE LAYOUT
	private Button btVoltar;
	private Button btIncluirNovaVenda;
	private Button btLogo;
	// FIM

	public ListarVendasWindow() throws ImageException, IOException {

		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		btIncluirNovaVenda = new Button("Inlcuir Nova Venda");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	@Override
	public void initUI() {
		
		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("RELATORIO DE VENDAS"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM
		
		add(btIncluirNovaVenda, LEFT + 10, BOTTOM - 10);
		add(btVoltar, RIGHT - 10, SAME);
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
