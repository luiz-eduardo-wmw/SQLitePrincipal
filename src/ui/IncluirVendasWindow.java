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
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
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
	private Edit editNumeroDoPedido;
	private Edit editNomeDoCliente;
	// FIM

	// CRIANDO OS BOTÕES DO SISTEMA
	private Button btVoltar;
	private Button btEnviar;
	private Button btExcluir;
	private Button btLogo;
	// FIM

	// IMPORTANDO A TABELA SORVETES
	private SorveteDAO sorveteDAO;
	private boolean atualizando;
	// FIM

	public IncluirVendasWindow() throws ImageException, IOException {
		this.atualizando = false;

		// CONFIGURANDO OS EDITS DE INSERÇÃO DE DADOS

		// INICIANDO OS EDITS
		editNumeroDoPedido = new Edit("999999999");
		editCodigo = new Edit("999999999");
		editNomeDoCliente = new Edit("99999999999999999999999999");
		editSabor = new Edit();
		editValorUnidade = new Edit("999999999,99");
		editValorVenda = new Edit("999999999,99");
		editValorTotal = new Edit("999999999,99");
		editEstoqueVenda = new Edit("999999999,99");
		editEstoqueAtivo = new Edit("999999999");
		editEstoqueVendido = new Edit();

		// SETANDO O MODO
		editNumeroDoPedido.setMode(Edit.NORMAL, true);
		editCodigo.setMode(Edit.NORMAL, true);
		editValorUnidade.setMode(Edit.CURRENCY, true);
		editValorVenda.setMode(Edit.CURRENCY, true);
		editValorTotal.setMode(Edit.CURRENCY, true);
		editEstoqueVenda.setMode(Edit.CURRENCY, true);
		editEstoqueAtivo.setMode(Edit.NORMAL, true);
		editEstoqueVendido.setMode(Edit.NORMAL, true);
		editNomeDoCliente.setMode(Edit.NORMAL, true);

		// SETANDO OS CARACTERES VALIDOS
		editNumeroDoPedido.setValidChars("1234567890");
		editCodigo.setValidChars("1234567890");
		editValorUnidade.setValidChars("0123456789");
		editValorVenda.setValidChars("0123456789");
		editValorTotal.setValidChars("0123456789");
		editEstoqueVenda.setValidChars("0123456789");
		editEstoqueAtivo.setValidChars("0123456789");
		editEstoqueVendido.setValidChars("0123456789");

		// ALINHAMENTO DO TEXTO
		editNumeroDoPedido.alignment = RIGHT;
		editCodigo.alignment = RIGHT;
		editSabor.alignment = RIGHT;
		editNomeDoCliente.alignment = RIGHT;
		editValorUnidade.alignment = RIGHT;
		editValorVenda.alignment = RIGHT;
		editValorTotal.alignment = RIGHT;
		editEstoqueVenda.alignment = RIGHT;
		editEstoqueAtivo.alignment = RIGHT;
		editEstoqueVendido.alignment = RIGHT;

		// SETANDO A EDIÇÃO
		editNumeroDoPedido.setEditable(true);
		editCodigo.setEditable(true);
		editSabor.setEditable(false);
		editNomeDoCliente.setEditable(true);
		editValorUnidade.setEditable(false);
		editValorVenda.setEditable(false);
		editValorTotal.setEditable(true);
		editEstoqueVenda.setEditable(true);
		editEstoqueAtivo.setEditable(false);
		editEstoqueVendido.setEditable(true);

		// FIM

		sorveteDAO = new SorveteDAO();
		new VendaDAO();

		// CONFIGURANDO OS BOTÕES DO SISTEMA
		btVoltar = new Button("Voltar");
		btEnviar = new Button("Enviar");
		btExcluir = new Button("Excluir");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM

	}

	public IncluirVendasWindow(Venda venda) throws ImageException, IOException, SQLException {
		this();
		this.atualizando = true;
		editNumeroDoPedido.setText(String.valueOf(venda.numeroDoPedido));
		editCodigo.setText(String.valueOf(venda.codigo));
		Sorvete sorvete = sorveteDAO.findByPrimaryKey(venda.codigo);
		editSabor.setText(sorvete.sabor);
		editNomeDoCliente.setText(venda.nomeDoCliente);
		editValorUnidade.setText(String.valueOf(sorvete.valorUnidade));
		editValorVenda.setText(String.valueOf(venda.valorVenda));
		editEstoqueAtivo.setText(String.valueOf(sorvete.estoqueAtivo));
		editEstoqueVenda.setText(String.valueOf(venda.estoqueVenda));
	}

	@Override
	public void initUI() {
		montaTela();
	}

	private void montaTela() {
		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("REGISTRANDO NOVA VENDA"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM

		// CRIANDO A TELA COM OS EDITS E LABEL
		add(new Label("Numero do Pedido"), LEFT + 10, AFTER + 10);
		add(editNumeroDoPedido, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Nome do Cliente/Empresa"), LEFT + 10, AFTER + 10);
		add(editNomeDoCliente, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Codigo do Produto"), LEFT + 10, AFTER + 10);
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
		// FIM

		if (atualizando) {
			add(btExcluir, BEFORE - 10, SAME);
		} else {
			add(btEnviar, BEFORE - 10, SAME);
		}
	}

	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();

	}

	private void reloadlistatualizar(int codigo) {
		Sorvete sorvete = null;
		try {
			sorvete = sorveteDAO.findByPrimaryKey(codigo);
			editSabor.setText(sorvete.sabor);
			editEstoqueAtivo.setText("" + sorvete.estoqueAtivo);
			editValorUnidade.setText("" + sorvete.valorUnidade);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					if (sorvete != null) {
						reloadlistatualizar(codigo);
					} else {
						new MessageBox("Info", "Código Indisponível").popup();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				reloadlistatualizar(codigo);
				break;
			}
			if (event.target == editEstoqueVenda) {
				double estoqueAtivo = Double.parseDouble(editEstoqueAtivo.getText().replace(",", "."));
				double estoqueVenda = Double.parseDouble(editEstoqueVenda.getText().replace(",", "."));
				double valorUnidade = Double.parseDouble(editValorUnidade.getText().replace(",", "."));
				if (estoqueVenda <= estoqueAtivo) {
					double valorTotal = estoqueVenda * valorUnidade;
					System.out.println(valorTotal);
					editValorVenda.setText("" + valorTotal);
				} else {
					new MessageBox("Info", "Estoque Indisponível").popup();
				}
			}
			break;
		case ControlEvent.PRESSED:

			if (event.target == btVoltar) {
				this.unpop();
			} else if (event.target == btEnviar) {
				insertVenda();
				atualizandoEstoqueSorvete();
				unpop();
			} else if (event.target == btExcluir) {
				excluirVenda();
				atualizandoEstoqueSorveteExcluir();
				unpop();
			}
		default:
			break;
		}

		super.onEvent(event);
	}

	private void atualizandoEstoqueSorvete() {
		double estoqueVenda = Double.parseDouble(editEstoqueVenda.getText().replace(",", "."));
		double estoqueAtivo = Double.parseDouble(editEstoqueAtivo.getText().replace(",", "."));
		int codigo = Integer.parseInt(editCodigo.getText().replace(",", "."));
		double estoquePosVenda = estoqueAtivo - estoqueVenda;
		atualizandoEstoque(codigo, estoquePosVenda);
	}

	private void atualizandoEstoqueSorveteExcluir() {
		double estoqueVenda = Double.parseDouble(editEstoqueVenda.getText().replace(",", "."));
		double estoqueAtivo = Double.parseDouble(editEstoqueAtivo.getText().replace(",", "."));
		int codigo = Integer.parseInt(editCodigo.getText().replace(",", "."));
		double estoquePosVenda = estoqueAtivo + estoqueVenda;
		atualizandoEstoque(codigo, estoquePosVenda);
	}
	
	public void atualizandoEstoque(int codigo, double estoquePosVenda) {
		try {
			if (sorveteDAO.atualizarEstoqueSorvete(codigo, estoquePosVenda)) {
				new MessageBox("Info", "Estoque Atualizado").popup();
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

	private Venda screenToDomain() throws Exception {
		String numeroDoPedido = editNumeroDoPedido.getText();
		String codigo = editCodigo.getText();
		String nomeDoCliente = editNomeDoCliente.getText();
		String sabor = editSabor.getText();
		String valorUnidade = editValorUnidade.getText();
		String valorVenda = editValorVenda.getText();
		String valorTotal = editValorTotal.getText();
		String estoqueAtivo = editEstoqueAtivo.getText();
		String estoqueVenda = editEstoqueVenda.getText();
		String estoqueVendido = editEstoqueVendido.getText();
		if (!validateFields(numeroDoPedido, codigo, nomeDoCliente, sabor, valorUnidade, valorVenda, estoqueAtivo,
				estoqueVenda))
			throw new Exception("Campos inválidos");

		Venda venda = createDomain(numeroDoPedido, codigo, nomeDoCliente, sabor, valorUnidade, valorVenda, valorTotal,
				estoqueAtivo, estoqueVenda, estoqueVendido);
		return venda;
	}

	private Venda createDomain(String numeroDoPedido, String codigo, String nomeDoCliente, String sabor,
			String valorUnidade, String valorVenda, String valorTotal, String estoqueAtivo, String estoqueVenda,
			String estoqueVendido) {
		double numeroDoPedidoAsInt = 0;
		double codigoAsInt = 0;
		double valorUnidadeAsDouble = 0;
		double valorVendaAsDouble = 0;
		double estoqueAtivoAsDouble = 0;
		double estoqueVendaAsDouble = 0;
		try {
			numeroDoPedido = numeroDoPedido.replace(",", ".");
			numeroDoPedidoAsInt = Double.parseDouble(numeroDoPedido);
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
		venda.numeroDoPedido = (int) numeroDoPedidoAsInt;
		venda.codigo = (int) codigoAsInt;
		venda.nomeDoCliente = nomeDoCliente;
		venda.sabor = sabor;
		venda.valorUnidade = valorUnidadeAsDouble;
		venda.valorVenda = valorVendaAsDouble;
		venda.estoqueAtivo = estoqueAtivoAsDouble;
		venda.estoqueVenda = estoqueVendaAsDouble;
		return venda;
	}

	private boolean validateFields(String numeroDoPedido, String codigo, String nomeDoCliente, String sabor,
			String valorUnidade, String valorVenda, String estoqueAtivo, String estoqueVenda) {
		if (numeroDoPedido.isEmpty()) {
			new MessageBox("Atenção", "Digite um número de Pedido!").popup();
			;
			return false;
		}
		if (codigo.isEmpty()) {
			new MessageBox("Atenção", "Digite um codigo!").popup();
			;
			return false;
		}
		if (nomeDoCliente.isEmpty()) {
			new MessageBox("Atenção", "Digite um Cliente/Empresa!").popup();
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
