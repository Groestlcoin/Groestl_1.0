package aplikacja.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import aplikacja.implementation.HashFunction;

public class NewGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textField_Input;
	private JTextField textField_Output;

	private JRadioButton rdbtnHex;
	private JRadioButton rdbtnText;

	private JButton btnOblicz;

	private ButtonGroup radioButtonGroup;

	public NewGui() {
		setSize(new Dimension(660, 480));
		setPreferredSize(new Dimension(660, 480));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelCalculate = new JPanel();
		tabbedPane.addTab("Obliczanie", null, panelCalculate, null);
		panelCalculate.setLayout(null);

		textField_Input = new JTextField();
		textField_Input.setBounds(10, 11, 619, 20);
		panelCalculate.add(textField_Input);
		textField_Input.setColumns(10);

		rdbtnHex = new JRadioButton("HEX");
		rdbtnHex.setBounds(10, 38, 109, 23);
		panelCalculate.add(rdbtnHex);

		rdbtnText = new JRadioButton("TEXT");
		rdbtnText.setBounds(10, 64, 109, 23);
		panelCalculate.add(rdbtnText);

		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(rdbtnHex);
		radioButtonGroup.add(rdbtnText);

		textField_Output = new JTextField();
		textField_Output.setBounds(10, 94, 619, 20);
		panelCalculate.add(textField_Output);
		textField_Output.setColumns(10);

		btnOblicz = new JButton("Oblicz");
		btnOblicz.setBounds(125, 38, 89, 49);
		btnOblicz.addActionListener(new ActionListenerOblicz());
		panelCalculate.add(btnOblicz);

		JPanel panelSummary = new JPanel();
		tabbedPane.addTab("Podsumowanie", null, panelSummary, null);

		JPanel panelHelp = new JPanel();
		tabbedPane.addTab("Pomoc", null, panelHelp, null);
	}

	class ActionListenerOblicz implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (radioButtonGroup.isSelected(rdbtnHex.getModel())) {
				HashFunction hf = new HashFunction();
				hf.calculateHash(textField_Input.getText());
				textField_Output.setText(hf.getHash());
			} else {

			}

		}

	}
}
