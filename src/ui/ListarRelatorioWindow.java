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

public class ListarRelatorioWindow extends Window {

	// OS BOTÕES DE LAYOUT
	private Button btVoltar;
	private Button btLogo;
	// FIM

	public ListarRelatorioWindow() throws ImageException, IOException {

		// FUNCOES SISTEMAS
		btVoltar = new Button("Voltar");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	@Override
	public void initUI() {

		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("RELATORIO GERAL DE VENDAS"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM
		
		// INSERINDO BOTÕES DO SISTEMA
		add(btVoltar, RIGHT - 10, BOTTOM - 10);
		// FIM
	}

	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}

	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btVoltar) {
				this.unpop();
			}
		}
	}

}
