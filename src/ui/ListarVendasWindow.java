package ui;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class ListarVendasWindow extends Window {

	// OS BOTÕES DE LAYOUT
	private Button btVoltar;
	private Button btIncluirNovaVenda;
	// FIM

	public ListarVendasWindow() {

		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		btIncluirNovaVenda = new Button("Inlcuir Nova Venda");
		// FIM

	}

	@Override
	public void initUI() {
		add(btIncluirNovaVenda, LEFT + 10, BOTTOM - 10, FILL - 240, PREFERRED);
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
				IncluirVendasWindow vendasWindow;
				vendasWindow = new IncluirVendasWindow();
				vendasWindow.popup();
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}
}
