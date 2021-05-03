package ui;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

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
	// FIM

	public IncluirVendasWindow() {

		// CONFIGURANDO OS EDITS DE INSERÇÃO DE DADOS
		//sabor
		editSabor = new Edit();
		
		//codigo
		editCodigo = new Edit("999999999");
		editCodigo.setMode(Edit.NORMAL, true);
		editCodigo.setValidChars("1234567890");
		
		//valorUnidade
		editValorUnidade = new Edit("999999999,99");
		editValorUnidade.setMode(Edit.CURRENCY, true);
		editValorUnidade.setValidChars("0123456789");
		
		//valorVenda
		editValorVenda = new Edit("999999999,99");
		editValorVenda.setMode(Edit.CURRENCY, true);
		editValorVenda.setValidChars("0123456789");
		
		//valorTotal
		editValorTotal = new Edit("999999999,99");
		editValorTotal.setMode(Edit.CURRENCY, true);
		editValorTotal.setValidChars("0123456789");
		
		//estoqueVenda
		editEstoqueVenda = new Edit("999999999,99");
		editEstoqueVenda.setMode(Edit.CURRENCY, true);
		editEstoqueVenda.setValidChars("0123456789");
		
		//estoqueAtivo
		editEstoqueAtivo = new Edit("999999999");
		editEstoqueAtivo.setMode(Edit.NORMAL, true);
		editEstoqueAtivo.setValidChars("0123456789");
		
		//estoqueVendido
		editEstoqueVendido = new Edit("999999999");
		editEstoqueVendido.setMode(Edit.NORMAL, true);
		editEstoqueVendido.setValidChars("0123456789");
		// FIM
		
		// CONFIGURANDO OS BOTÕES DO SISTEMA
		btVoltar = new Button("Voltar");
		//FIM

	}

	@Override
	public void initUI() {
		
		//CRIANDO A TELA COM OS EDITS E LABEL
		add(new Label("Codigo"), LEFT + 10, TOP + 10);
		add(editCodigo, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);
		
		//deverá importar da outra tabela com base no código digitado
		add(new Label("Sabor"), LEFT + 10, AFTER + 10);
		add(editSabor, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		//deverá importar da outra tabela com base no código digitado
		add(new Label("Valor do Sorvete"), LEFT + 10, AFTER + 10);
		add(editValorUnidade, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		//deverá importar da outra tabela com base no código digitado
		add(new Label("Estoque do Sorvete"), LEFT + 10, AFTER + 10);
		add(editEstoqueAtivo, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);
		
		add(new Label("Quantidade da Venda"), LEFT + 10, AFTER + 10);
		add(editEstoqueVenda, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);
		
		add(new Label("Total da Venda"), LEFT + 10, AFTER + 10);
		add(editValorVenda, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);
		// FIM
		
		// ADICIONANDO NA TELA O BOTÃO VOLTAR
		add(btVoltar, RIGHT - 10, BOTTOM - 10);
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
		}
	}
	
	
}