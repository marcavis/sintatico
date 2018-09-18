package sintatico;

import org.eclipse.swt.graphics.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		shell.setSize(400, 400);

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
				System.out.println(selected);
				try {
					String codigoFonte = readFile(selected, Charset.defaultCharset());
					System.out.println(codigoFonte);
					caixaDeCodigo.setText(codigoFonte);
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
		caixaDeCodigo.setBounds(30, 10, 332, 266);
		//styledText.setText("fasdfasdf\nasdjasdij");

		Button btnHilight = new Button(shell, SWT.NONE);
		btnHilight.setBounds(158, 282, 100, 36);
		btnHilight.setText("Hilight");
		btnHilight.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				StyleRange sr = new StyleRange();
				sr.foreground = new Color(display, 255, 75, 0);
				sr.start = 0;
				sr.length = 31;
				caixaDeCodigo.setStyleRange(sr);
				//Color orange = new Color(display, 255, 127, 0);
				//caixaDeCodigo.setLineBackground(2, 1, orange);
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