package ui;

import java.sql.SQLException;

import dao.SorveteDAO;
import domain.Sorvete;
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

public class IncluirSorvetesWindow extends Window {

	private Edit editSabor;
	private Edit editValor;
	private Edit editEstoque;
	private Edit editCodigo;

	// OS BOTÕES DE LAYOUT
	private Button btInserir;
	private Button btVoltar;
	private Button btAtualizar;
	private Button btExcluir;
	private Button btLogo;
	// FIM

	private SorveteDAO sorveteDAO;

	private boolean atualizando;

	public IncluirSorvetesWindow() throws ImageException, IOException {
		this.atualizando = false;
		editSabor = new Edit();
		editSabor.alignment = RIGHT;
		
		editCodigo = new Edit("999999999");
		editCodigo.setMode(Edit.NORMAL, true);
		editCodigo.setValidChars("1234567890");
		editCodigo.alignment = RIGHT;

		editValor = new Edit("999999999,99");
		editValor.setMode(Edit.CURRENCY, true);
		editValor.setValidChars("0123456789");
		editValor.alignment = RIGHT;

		editEstoque = new Edit("999999999");
		editEstoque.setMode(Edit.NORMAL, true);
		editEstoque.setValidChars("0123456789UN");
		editEstoque.alignment = RIGHT;


		sorveteDAO = new SorveteDAO();

		// FUNCOES SISTEMAS
		btInserir = new Button("Inserir");
		btVoltar = new Button("Voltar");
		btAtualizar = new Button("Atualizar");
		btExcluir = new Button("Excluir");
		btLogo = new Button(new Image("/resources/logoWMW 80x40.png"), Button.BORDER_NONE);
		// FIM
	}

	public IncluirSorvetesWindow(Sorvete sorvete) throws ImageException, IOException {
		this();
		this.atualizando = true;
		editCodigo.setText(String.valueOf(sorvete.codigo));
		editValor.setText(String.valueOf(sorvete.valorUnidade));
		editEstoque.setText(String.valueOf(sorvete.estoqueAtivo));

		editSabor.setText(sorvete.sabor);
	}

	@Override
	public void initUI() {

		// INSERINDO A LOGO E O TEXTO
		add(btLogo, LEFT + 10, TOP);
		add(new Label("REGISTRANDO UM PRODUTO"), AFTER + 65, SAME + 10, FILL - 10, PREFERRED);
		// FIM

		add(new Label("Codigo"), LEFT + 10, AFTER + 10);
		add(editCodigo, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Sabor"), LEFT + 10, AFTER + 10);
		add(editSabor, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Valor"), LEFT + 10, AFTER + 10);
		add(editValor, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(new Label("Estoque"), LEFT + 10, AFTER + 10);
		add(editEstoque, LEFT + 10, AFTER + 5, FILL - 300, PREFERRED);

		add(btVoltar, RIGHT - 10, BOTTOM - 10);

		if (atualizando) {
			add(btAtualizar, BEFORE - 10, SAME);
			add(btExcluir, BEFORE - 10, SAME);
		} else {
			add(btInserir, BEFORE - 10, SAME);
		}
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
			} else if (event.target == btInserir) {
				insertSorvete();
				unpop();
			} else if (event.target == btAtualizar) {
				atualizarSorvete();
				unpop();
			} else if (event.target == btExcluir) {
				excluirSorvete();
				unpop();
			}
		default:
			break;
		}
		super.onEvent(event);
	}

	public void insertSorvete() {
		try {
			Sorvete sorvete = screenToDomain();
			if (sorvete == null)
				return;
			if (sorveteDAO.insertSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Inserido").popup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizarSorvete() {
		try {
			Sorvete sorvete = screenToDomain();
			if (sorvete == null)
				return;
			if (sorveteDAO.atualizarSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Atualizado").popup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluirSorvete() {
		try {
			Sorvete sorvete = screenToDomain();
			if (sorvete == null)
				return;
			if (sorveteDAO.excluirSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Excluído!").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}

	private Sorvete screenToDomain() throws Exception {
		String codigo = editCodigo.getText();
		String sabor = editSabor.getText();
		String valor = editValor.getText();
		String estoque = editEstoque.getText();
		if (!validateFields(codigo, sabor, valor, estoque))
			throw new Exception("Campos inválidos");

		Sorvete sorvete = createDomain(codigo, sabor, valor, estoque);
		return sorvete;
	}

	private Sorvete createDomain(String codigo, String sabor, String valor, String estoque) {
		double codigoAsInt = 0;
		double valorAsDouble = 0;
		double estoqueAsInt = 0;
		try {
			codigo = codigo.replace(",", ".");
			codigoAsInt = Double.parseDouble(codigo);
			valor = valor.replace(",", ".");
			valorAsDouble = Double.parseDouble(valor);
			estoque = estoque.replace(",", ".");
			estoqueAsInt = Double.parseDouble(estoque);
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		Sorvete sorvete = new Sorvete();
		sorvete.codigo = (int) codigoAsInt;
		sorvete.sabor = sabor;
		sorvete.valorUnidade = valorAsDouble;
		sorvete.estoqueAtivo = (int) estoqueAsInt;
		return sorvete;
	}

	private boolean validateFields(String codigo, String sabor, String valor, String estoque) {
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

		if (valor.isEmpty()) {
			new MessageBox("Atenção", "Digite um valor!").popup();
			;
			return false;
		}

		if (estoque.isEmpty()) {
			new MessageBox("Atenção", "Digite um estoque!").popup();
			;
			return false;
		}
		return true;
	}
}
