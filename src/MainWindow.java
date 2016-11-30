import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.tim.nuspacker.Starter;


public class MainWindow extends JPanel implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1431142157179047996L;
	static JTextField inputText;
	static JTextField outputText;
	static JTextField titleidText;
	static JTextField keyText;
	static JTextField keyWithText;
	static JButton btnGo;
	private JTextArea statusLabel;



	public void initialise()
	{
		String path = "";
		try {
			path = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputText = new JTextField();
		outputText = new JTextField();
		titleidText = new JTextField();
		keyText = new JTextField();
		keyWithText = new JTextField();

		setUpTextBox(inputText);
		setUpTextBox(outputText);
		setUpTextBox(titleidText);
		setUpTextBox(keyText);
		setUpTextBox(keyWithText);

		inputText.setText(path);
		outputText.setText(path);

		JPanel panel = new JPanel();
		setUpLabel(inputText, "Directory with encrypted data:", panel);
		add(panel);

		JLabel blank = new JLabel(" ");
		blank.setMaximumSize(new Dimension(20, 20));
		blank.setMinimumSize(new Dimension(20, 20));

		add(blank);

		panel = new JPanel();
		setUpLabel(outputText, "Directory to save package to:", panel);
		add(panel);

		blank = new JLabel(" ");
		add(blank);

		panel = new JPanel();
		setUpLabel(titleidText, "TitleId of package (part after 00050000):", panel);
		add(panel);

		blank = new JLabel(" ");
		add(blank);

		panel = new JPanel();
		setUpLabel(keyText, "Encryption key:", panel);
		add(panel);

		blank = new JLabel(" ");
		add(blank);

		panel = new JPanel();
		setUpLabel(keyWithText, "Key to encrypt encrytion key:", panel);
		add(panel);

		blank = new JLabel(" ");
		add(blank);

		btnGo = new JButton("Go");
		btnGo.setActionCommand("go");
        btnGo.addActionListener(this);
        btnGo.setMaximumSize(new Dimension(100, 40));
        btnGo.setMinimumSize(new Dimension(100, 40));
		add(btnGo);

		statusLabel = new JTextArea();
		statusLabel.setMaximumSize(new Dimension(1000, 100));
		statusLabel.setMinimumSize(new Dimension(1000, 100));
		statusLabel.setEditable(false);
		PrintStream printStream = new PrintStream(new ConsoleOutput(statusLabel));
		System.setOut(printStream);
		System.setErr(printStream);
        add(new JScrollPane(statusLabel));

	}

	public static void patch(Component c) {
        Set<KeyStroke>
        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
        c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes);
        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
        c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes);
    }

	private void setUpTextBox(JTextField box)
	{
		box.setMaximumSize(new Dimension(400, 20));
		box.setMinimumSize(new Dimension(400, 20));
		box.setEditable(true);
	}

	private void setUpLabel(JTextField box, String text, JPanel panel)
	{
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		JLabel label = new JLabel(text);
		label.setMaximumSize(new Dimension(300, 20));
		label.setMinimumSize(new Dimension(300, 20));
		JLabel blank = new JLabel(" ");
		blank.setMaximumSize(new Dimension(20, 20));
		blank.setMinimumSize(new Dimension(20, 20));

		panel.add(label);
		panel.add(blank);
		panel.add(box);
	}

	public MainWindow()
	{
		initialise();
	}
	private static void createWindow()
	{
		JFrame frame = new JFrame("NUSPacker GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));

        MainWindow newContentPane = new MainWindow();
        newContentPane.setLayout(new BoxLayout(newContentPane, BoxLayout.PAGE_AXIS));
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.setLocationRelativeTo(null);

        frame.pack();

        inputText.requestFocusInWindow();

        frame.setVisible(true);
	}
	public static void main(String[] args)
	{
		createWindow();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
        {
		case "go":
			String [] args = {inputText.getText(), outputText.getText(), titleidText.getText(), keyText.getText(), keyWithText.getText()};

			Starter.main(args);

        }

	}

}

