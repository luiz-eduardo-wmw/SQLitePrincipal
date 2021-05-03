package ui;

import totalcross.io.IOException;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class IncluirVendasWindow extends Window {

	// CRIANDO OS EDITS DE INSERÇÃO DE DADOS
	private Edit editCodigo;
	private Edit editSabor;
	private Edit editValorUnidade;
	private Edit editValorVenda;
	private Edit editValorTotal;
	private Edit editEstoqueVenda;
	private Edit editEstoqueAtivo;
	private Edit editEstoqueVendido;
	// FIM

	// CRIANDO OS BOTÕES DO SISTEMA
	private Button btVoltar;
	private Button btEnviar;
	private Button btLogo;
	// FIM

	public IncluirVendasWindow() throws ImageException, IOException {

		// CONFIGURANDO OS EDITS DE INSERÇÃO DE DADOS
		// sabor
		editSabor = new Edit();

		// codigo
		editCodigo = new Edit("999999999");
		editCodigo.setMode(Edit.NORMAL, true);
		editCodigo.setValidChars("1234567890");

		// valorUnidade
		editValorUnidade = new Edit("999999999,99");
		editValorUnidade.setMode(Edit.CURRENCY, true);
		editValorUnidade.setValidChars("0123456789");

		// valorVenda
		editValorVenda = new Edit("999999999,99");
		editValorVenda.setMode(Edit.CURRENCY, true);
		editValorVenda.setValidChars("0123456789");

		// valorTotal
		editValorTotal = new Edit("999999999,99");
		editValorTotal.setMode(Edit.CURRENCY, true);
		editValorTotal.setValidChars("0123456789");

		// estoqueVenda
		editEstoqueVenda = new Edit("999999999,99");
		editEstoqueVenda.setMode(Edit.CURRENCY, true);
		editEstoqueVenda.setValidChars("0123456789");

		// estoqueAtivo
		editEstoqueAtivo = new Edit("999999999");
		editEstoqueAtivo.setMode(Edit.NORMAL, true);
		editEstoqueAtivo.setValidChars("0123456789");

		// estoqueVendido
		editEstoqueVendido = new Edit("999999999");
		editEstoqueVendido.setMode(Edit.NORMAL, true);
		editEstoqueVendido.setValidChars("0123456789");
		// FIM


		// CONFIGURANDO OS BOTÕES DO SISTEMA
		btVoltar = new Button("Voltar");
		btEnviar = new Button("Enviar");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	@Override
	public void initUI() {

		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("REGISTRANDO NOVA VENDA"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM
		
		// CRIANDO A TELA COM OS EDITS E LABEL
		add(new Label("Codigo"), LEFT + 10, AFTER + 10);
		add(editCodigo, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		// deverá importar da outra tabela com base no código digitado
		add(new Label("Sabor"), LEFT + 10, AFTER + 10);
		add(editSabor, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		// deverá importar da outra tabela com base no código digitado
		add(new Label("Valor do Sorvete"), LEFT + 10, AFTER + 10);
		add(editValorUnidade, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		// deverá importar da outra tabela com base no código digitado
		add(new Label("Estoque do Sorvete"), LEFT + 10, AFTER + 10);
		add(editEstoqueAtivo, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Quantidade da Venda"), LEFT + 10, AFTER + 10);
		add(editEstoqueVenda, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Total da Venda"), LEFT + 10, AFTER + 10);
		add(editValorVenda, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);
		// FIM
		

		// ADICIONANDO NA TELA O BOTÃO VOLTAR
		add(btVoltar, RIGHT - 10, BOTTOM - 10);
		add(btEnviar, BEFORE - 10, BOTTOM - 10);
		// FIM

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
			}
		case ControlEvent.FOCUS_OUT:
			if (event.target == editCodigo) {
				System.out.println(editCodigo.getText());
				break;
			}
		}
	}
}