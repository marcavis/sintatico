package sintatico;

import org.eclipse.swt.graphics.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;

public class Principal {
	
	Display display;

	Shell shell;
	StyledText caixaDeCodigo;

	Principal() {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(600, 600);

		shell.setText("Analisador LMS");
		Menu m = new Menu(shell, SWT.BAR);
		final MenuItem file = new MenuItem(m, SWT.CASCADE);
		file.setText("&File");
		final Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
		file.setMenu(filemenu);
		final MenuItem openItem = new MenuItem(filemenu, SWT.PUSH);
		openItem.setText("&Open\tCTRL+O");
		openItem.setAccelerator(SWT.CTRL + 'O');
		final MenuItem saveItem = new MenuItem(filemenu, SWT.PUSH);
		saveItem.setText("&Save\tCTRL+S");
		saveItem.setAccelerator(SWT.CTRL + 'S');
		final MenuItem separator = new MenuItem(filemenu, SWT.SEPARATOR);
		final MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
		exitItem.setText("E&xit");

		class Open implements SelectionListener {
			public void widgetSelected(SelectionEvent event) {
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Open");
				fileDialog.setFilterPath("C:/");	
				String[] filterExt = { "*.*", "*.txt", "*.lms", "*.*"};
				fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				if(selected == null)
					return;
				System.out.println(selected);
				try {
					String codigoFonte = readFile(selected, Charset.defaultCharset());
					System.out.println(codigoFonte);
					//Usa-se a função trim() para mandar o código ao analisador sem espaços vazios
					//à esquerda e à direita, para que o léxico não se confunda ao procurar o fim do arquivo.
					caixaDeCodigo.setText(codigoFonte.trim());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent event) {

			}
		}

		class Save implements SelectionListener {
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setText("Save");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.txt", "*.lms", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		}
		openItem.addSelectionListener(new Open());
		saveItem.addSelectionListener(new Save());

		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
						| SWT.YES | SWT.NO);
				messageBox.setMessage("Do you really want to exit?");
				messageBox.setText("Exiting Application");
				int response = messageBox.open();
				if (response == SWT.YES)
					System.exit(0);
			}
		});
		shell.setMenuBar(m);

		caixaDeCodigo = new StyledText(shell, SWT.BORDER);
		caixaDeCodigo.setBounds(30, 10, 532, 466);

		Button btnLexico = new Button(shell, SWT.NONE);
		btnLexico.setBounds(138, 482, 100, 36);
		btnLexico.setText("Léxico");
		btnLexico.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//atualizar a janela de código para remover linhas que tenham sido pintadas anteriormente
				caixaDeCodigo.setText(caixaDeCodigo.getText());
				int linhaDoErro = -1;
				String msgErro = "";
				ArrayList<String[]> tabelaDeTokens = new ArrayList<String[]>();
				try {
					tabelaDeTokens = Sintatico.soLexico(shell, caixaDeCodigo.getText().trim());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					linhaDoErro = ((SintaticoException) e1).getLinha();
					msgErro = ((SintaticoException) e1).getAviso();
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR
							| SWT.OK);
					messageBox.setMessage(msgErro);
					messageBox.setText("Erro");
					messageBox.open();
					caixaDeCodigo.setLineBackground(linhaDoErro, 1, new Color(display, 255,127,127));
				}
				//informar sucesso se não foi encontrado erro
				if (linhaDoErro == -1) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION
							| SWT.OK);
					messageBox.setMessage("Arquivo processado com sucesso.");
					messageBox.setText("Sucesso!");
					messageBox.open();
				}
				
				Shell shellTabela = new Shell(display);
				shellTabela.setSize(400, 300);
				
				Table tabela = new Table(shellTabela, SWT.BORDER | SWT.V_SCROLL
						| SWT.H_SCROLL);
				
				String[] titulos = { "Código", "Descrição", "Lexema" };
				for (int i = 0; i < titulos.length; i++) {
					TableColumn column = new TableColumn(tabela, SWT.NONE);
					column.setText(titulos[i]);
				}
				for (int linha = 0; linha < tabelaDeTokens.size(); linha++) {
					TableItem esteToken = new TableItem(tabela, SWT.NONE);
					String[] estaLinha = tabelaDeTokens.get(linha); 
					esteToken.setText(new String[] {estaLinha[0], estaLinha[1], estaLinha[2]});
				}
				tabela.setHeaderVisible(true);
				tabela.setLinesVisible(true);
				tabela.setBounds(20, 20, 360, 200);
				tabela.getColumn(0).pack();
				tabela.getColumn(1).pack();
				tabela.getColumn(2).pack();
				
				shellTabela.pack();
				shellTabela.open();
				for (String[] s : tabelaDeTokens) {
					for (String t : s) {
						System.out.print(t);
					}
					System.out.println("");
				}
			}
		});
		
		Button btnSintatico = new Button(shell, SWT.NONE);
		btnSintatico.setBounds(258, 482, 100, 36);
		btnSintatico.setText("Sintático");
		btnSintatico.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//atualizar a janela de código para remover linhas que tenham sido pintadas anteriormente
				caixaDeCodigo.setText(caixaDeCodigo.getText());
				int linhaDoErro = -1;
				String msgErro = "";
				try {
					Sintatico.analisar(shell, caixaDeCodigo.getText().trim());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					linhaDoErro = ((SintaticoException) e1).getLinha();
					msgErro = ((SintaticoException) e1).getAviso();
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR
							| SWT.OK);
					messageBox.setMessage(msgErro);
					messageBox.setText("Erro");
					messageBox.open();
					caixaDeCodigo.setLineBackground(linhaDoErro, 1, new Color(display, 255,127,127));
				}
				//informar sucesso se não foi encontrado erro
				if (linhaDoErro == -1) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION
							| SWT.OK);
					messageBox.setMessage("Arquivo processado com sucesso.");
					messageBox.setText("Sucesso!");
					messageBox.open();
				}
			}
		});
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public static void main(String[] argv) {
		new Principal();
	}
}
