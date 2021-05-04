package ui;

import java.sql.SQLException;

import dao.SorveteDAO;
import dao.VendaDAO;
import domain.Sorvete;
import domain.Venda;
import totalcross.io.IOException;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
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

	// IMPORTANDO A TABELA SORVETES
	private SorveteDAO sorveteDAO;
	private VendaDAO vendaDAO;
	// FIM

	private boolean atualizando;

	public IncluirVendasWindow() throws ImageException, IOException {
		this.atualizando = false;

		// CONFIGURANDO OS EDITS DE INSERÇÃO DE DADOS
		// sabor
		editSabor = new Edit();
		editSabor.setEditable(false);

		// codigo
		editCodigo = new Edit("999999999");
		editCodigo.setMode(Edit.NORMAL, true);
		editCodigo.setValidChars("1234567890");

		// valorUnidade
		editValorUnidade = new Edit("999999999,99");
		editValorUnidade.setMode(Edit.CURRENCY, true);
		editValorUnidade.setValidChars("0123456789");
		editValorUnidade.setEditable(false);

		// valorVenda
		editValorVenda = new Edit("999999999,99");
		editValorVenda.setMode(Edit.CURRENCY, true);
		editValorVenda.setValidChars("0123456789");
		editValorVenda.setEditable(false);

		// valorTotal
		editValorTotal = new Edit("999999999,99");
		editValorTotal.setMode(Edit.CURRENCY, true);
		editValorTotal.setValidChars("0123456789");

		// estoqueVenda
		editEstoqueVenda = new Edit("999999999,99");
		editEstoqueVenda.setMode(Edit.CURRENCY, true);
		editEstoqueVenda.setValidChars("0123456789");
		editEstoqueVenda.setEditable(true);

		// estoqueAtivo
		editEstoqueAtivo = new Edit("999999999");
		editEstoqueAtivo.setMode(Edit.NORMAL, true);
		editEstoqueAtivo.setValidChars("0123456789");
		editEstoqueAtivo.setEditable(false);

		// estoqueVendido
		editEstoqueVendido = new Edit("999999999");
		editEstoqueVendido.setMode(Edit.NORMAL, true);
		editEstoqueVendido.setValidChars("0123456789");
		// FIM

		sorveteDAO = new SorveteDAO();
		vendaDAO = new VendaDAO();

		// CONFIGURANDO OS BOTÕES DO SISTEMA
		btVoltar = new Button("Voltar");
		btEnviar = new Button("Enviar");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	public IncluirVendasWindow(Venda venda) throws ImageException, IOException {
		this();
		this.atualizando = true;
		editCodigo.setText(String.valueOf(venda.codigo));
		editSabor.setText(venda.sabor);
		editValorUnidade.setText(String.valueOf(venda.valorUnidade));
		editValorVenda.setText(String.valueOf(venda.valorVenda));
		editValorTotal.setText(String.valueOf(venda.valorTotal));
		editEstoqueAtivo.setText(String.valueOf(venda.estoqueAtivo));
		editEstoqueVenda.setText(String.valueOf(venda.estoqueVenda));
		editEstoqueVendido.setText(String.valueOf(venda.estoqueVendido));
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
		case ControlEvent.FOCUS_OUT:
			Sorvete sorvete = null;

			if (event.target == editCodigo && !editCodigo.getText().isEmpty()) {
				int codigo = Integer.parseInt(editCodigo.getText());
				try {
					sorvete = sorveteDAO.findByPrimaryKey(codigo);
					System.out.println(sorvete);
					editSabor.setText(sorvete.sabor);
					editEstoqueAtivo.setText("" + sorvete.estoqueAtivo);
					editValorUnidade.setText("" + sorvete.valorUnidade);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			if (event.target == editEstoqueVenda) {
				// editEstoqueVenda.getText();

				double estoqueVenda = Double.parseDouble(editEstoqueVenda.getText().replace(",", "."));
				double valorUnidade = Double.parseDouble(editValorUnidade.getText().replace(",", "."));
				double valorTotal = estoqueVenda * valorUnidade;
				System.out.println(valorTotal);
				editValorVenda.setText("" + valorTotal);
				// editValorVenda.setText("" + editEstoqueVenda.get());
			}
			break;
		case ControlEvent.PRESSED:
			if (event.target == btVoltar) {
				this.unpop();
			} else if (event.target == btEnviar) {
				insertVenda();
				unpop();
			}
		default:
			break;
		}

		super.onEvent(event);
	}

	public void atualizandoEstoque() {
		try {
			Venda venda = screenToDomainEstoque();
			if (venda == null)
				return;
			if (VendaDAO.atualizarVenda(venda)) {
				new MessageBox("Info", "Venda Atualizada").popup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertVenda() {
		try {
			Venda venda = screenToDomain();
			if (venda == null)
				return;
			if (VendaDAO.insertVenda(venda)) {
				new MessageBox("Info", "Venda Inserida").popup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizarVenda() {
		try {
			Venda venda = screenToDomain();
			if (venda == null)
				return;
			if (VendaDAO.atualizarVenda(venda)) {
				new MessageBox("Info", "Venda Atualizada").popup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluirVenda() {
		try {
			Venda venda = screenToDomain();
			if (venda == null)
				return;
			if (VendaDAO.excluirVenda(venda)) {
				new MessageBox("Info", "Venda Excluída!").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}

	private Venda screenToDomainEstoque() throws Exception {
		String codigo = editCodigo.getText();
		String sabor = editSabor.getText();
		String valorUnidade = editValorUnidade.getText();
		String valorVenda = editValorVenda.getText();
		String valorTotal = editValorTotal.getText();
		String estoqueAtivo = editEstoqueAtivo.getText();
		String estoqueVenda = editEstoqueVenda.getText();
		String estoqueVendido = editEstoqueVendido.getText();
		if (!validateFieldsEstoque(codigo, sabor, valorUnidade, valorVenda, valorTotal, estoqueAtivo, estoqueVenda,
				estoqueVendido))
			throw new Exception("Campos inválidos");

		Venda venda = createDomainEstoque(codigo, sabor, valorUnidade, valorVenda, valorTotal, estoqueAtivo,
				estoqueVenda, estoqueVendido);
		return venda;
	}

	private Venda createDomainEstoque(String codigo, String sabor, String valorUnidade, String valorVenda,
			String valorTotal, String estoqueAtivo, String estoqueVenda, String estoqueVendido) {
		double codigoAsInt = 0;
		double valorUnidadeAsDouble = 0;
		double valorVendaAsDouble = 0;
		double valorTotalAsDouble = 0;
		double estoqueAtivoAsDouble = 0;
		double estoqueVendaAsDouble = 0;
		double estoqueVendidoAsDouble = 0;
		try {
			codigo = codigo.replace(",", ".");
			codigoAsInt = Double.parseDouble(codigo);
			valorUnidade = valorUnidade.replace(",", ".");
			valorUnidadeAsDouble = Double.parseDouble(valorUnidade);
			valorVenda = valorVenda.replace(",", ".");
			valorVendaAsDouble = Double.parseDouble(valorVenda);
			valorTotal = valorTotal.replace(",", ".");
			valorTotalAsDouble = Double.parseDouble(valorTotal);
			estoqueAtivo = estoqueAtivo.replace(",", ".");
			estoqueAtivoAsDouble = Double.parseDouble(estoqueAtivo);
			estoqueVenda = estoqueVenda.replace(",", ".");
			estoqueVendaAsDouble = Double.parseDouble(estoqueVenda);
			estoqueVendido = estoqueVendido.replace(",", ".");
			estoqueVendidoAsDouble = Double.parseDouble(estoqueVendido);
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		Venda venda = new Venda();
		venda.codigo = (int) codigoAsInt;
		venda.sabor = sabor;
		venda.valorUnidade = valorUnidadeAsDouble;
		venda.valorVenda = valorVendaAsDouble;
		venda.valorTotal = valorTotalAsDouble;
		venda.estoqueAtivo = estoqueAtivoAsDouble;
		venda.estoqueVenda = estoqueVendaAsDouble;
		venda.estoqueVendido = estoqueVendidoAsDouble;
		return venda;
	}

	private boolean validateFieldsEstoque(String codigo, String sabor, String valorUnidade, String valorVenda,
			String valorTotal, String estoqueAtivo, String estoqueVenda, String estoqueVendido) {
		if (codigo.isEmpty()) {
			new MessageBox("Atenção", "Digite um codigo!").popup();
			;
			return false;
		}

		if (sabor.isEmpty()) {
			new MessageBox("Atenção", "Digite um sabor!").popup();
			;
			return false;
		}

		if (valorUnidade.isEmpty()) {
			new MessageBox("Atenção", "Digite o valor do Sorvete!").popup();
			;
			return false;
		}

		if (valorVenda.isEmpty()) {
			new MessageBox("Atenção", "Digite o valor da Venda!").popup();
			;
			return false;
		}

		if (valorTotal.isEmpty()) {
			new MessageBox("Atenção", "Digite um valor Total!").popup();
			;
			return false;
		}

		if (estoqueAtivo.isEmpty()) {
			new MessageBox("Atenção", "Digite o Estoque Ativo!").popup();
			;
			return false;
		}

		if (estoqueVenda.isEmpty()) {
			new MessageBox("Atenção", "Digite a quantidade de produto(s) a se(rem) vendido(s)!").popup();
			;
			return false;
		}
		return true;
	}

	private Venda screenToDomain() throws Exception {
		String codigo = editCodigo.getText();
		String sabor = editSabor.getText();
		String valorUnidade = editValorUnidade.getText();
		String valorVenda = editValorVenda.getText();
		String valorTotal = editValorTotal.getText();
		String estoqueAtivo = editEstoqueAtivo.getText();
		String estoqueVenda = editEstoqueVenda.getText();
		String estoqueVendido = editEstoqueVendido.getText();
		if (!validateFields(codigo, sabor, valorUnidade, valorVenda, estoqueAtivo, estoqueVenda))
			throw new Exception("Campos inválidos");

		Venda venda = createDomain(codigo, sabor, valorUnidade, valorVenda, valorTotal, estoqueAtivo, estoqueVenda,
				estoqueVendido);
		return venda;
	}

	private Venda createDomain(String codigo, String sabor, String valorUnidade, String valorVenda, String valorTotal,
			String estoqueAtivo, String estoqueVenda, String estoqueVendido) {
		double codigoAsInt = 0;
		double valorUnidadeAsDouble = 0;
		double valorVendaAsDouble = 0;
		double estoqueAtivoAsDouble = 0;
		double estoqueVendaAsDouble = 0;
		try {
			codigo = codigo.replace(",", ".");
			codigoAsInt = Double.parseDouble(codigo);
			valorUnidade = valorUnidade.replace(",", ".");
			valorUnidadeAsDouble = Double.parseDouble(valorUnidade);
			valorVenda = valorVenda.replace(",", ".");
			valorVendaAsDouble = Double.parseDouble(valorVenda);
			estoqueAtivo = estoqueAtivo.replace(",", ".");
			estoqueAtivoAsDouble = Double.parseDouble(estoqueAtivo);
			estoqueVenda = estoqueVenda.replace(",", ".");
			estoqueVendaAsDouble = Double.parseDouble(estoqueVenda);
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		Venda venda = new Venda();
		venda.codigo = (int) codigoAsInt;
		venda.sabor = sabor;
		venda.valorUnidade = valorUnidadeAsDouble;
		venda.valorVenda = valorVendaAsDouble;
		venda.estoqueAtivo = estoqueAtivoAsDouble;
		venda.estoqueVenda = estoqueVendaAsDouble;
		return venda;
	}

	private boolean validateFields(String codigo, String sabor, String valorUnidade, String valorVenda,
			String estoqueAtivo, String estoqueVenda) {
		if (codigo.isEmpty()) {
			new MessageBox("Atenção", "Digite um codigo!").popup();
			;
			return false;
		}

		if (sabor.isEmpty()) {
			new MessageBox("Atenção", "Digite um sabor!").popup();
			;
			return false;
		}

		if (valorUnidade.isEmpty()) {
			new MessageBox("Atenção", "Digite o valor do Sorvete!").popup();
			;
			return false;
		}

		if (valorVenda.isEmpty()) {
			new MessageBox("Atenção", "Digite o valor da Venda!").popup();
			;
			return false;
		}

		if (estoqueAtivo.isEmpty()) {
			new MessageBox("Atenção", "Digite o Estoque Ativo!").popup();
			;
			return false;
		}

		if (estoqueVenda.isEmpty()) {
			new MessageBox("Atenção", "Digite a quantidade de produto(s) a se(rem) vendido(s)!").popup();
			;
			return false;
		}
		return true;
	}

}