package forms;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import help.Elements;

import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class Show {

	private JFrame frmPrikazIgara;
	private JFrame otherFrame;
	private JLabel[] arrLabel = new JLabel[16];
	private JLabel[] arrPlayer = new JLabel[4];
	private JButton[] arrButton = new JButton[16];
	private JPanel[] arrPanel = new JPanel[16];
	private JPanel panel1 = null;
	private JPanel panel2 = null;
	private JPanel panelInside1 = null;
	private JPanel panelInside2 = null;
	private JPanel[] arrRootPanel = new JPanel[4];
	
	public Show(JFrame otherFrame) {
		this.otherFrame = otherFrame;
		initialize();
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private void initialize() {
		frmPrikazIgara = new JFrame();
		frmPrikazIgara.setTitle("Prikaz Igara");
		frmPrikazIgara.getContentPane().setBackground(new Color(255, 255, 255));
		
		panel1 = new JPanel();
		panel1.setBackground(new Color(255, 255, 255));
		frmPrikazIgara.getContentPane().add(panel1, BorderLayout.WEST);
		
		panel2 = new JPanel();
		panel2.setBorder(new EmptyBorder(30, 30, 30, 30));
		panel2.setBackground(new Color(255, 255, 255));
		frmPrikazIgara.getContentPane().add(panel2, BorderLayout.CENTER);
		panel2.setLayout(new BorderLayout(0, 0));
		
		panelInside1 = new JPanel();
		panelInside1.setBackground(new Color(255, 255, 255));
		panel2.add(panelInside1, BorderLayout.NORTH);
		panelInside1.setLayout(new BorderLayout(0, 0));
		
		JLabel label1 = new JLabel("Vrijeme Trajanja Igre: 120s");
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelInside1.add(label1);
		label1.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panelInside2 = new JPanel();
		panelInside2.setBackground(new Color(255, 255, 255));
		panel2.add(panelInside2, BorderLayout.CENTER);
		panelInside2.setLayout(new GridLayout(4, 1));
		frmPrikazIgara.setBounds(100, 100, 450, 300);
		frmPrikazIgara.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frmPrikazIgara.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	otherFrame.setEnabled(true);
		    }
		});
		
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.white);
		myPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		myPanel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		String []fileNames = new String[Elements.fileManagement.getNumberOfGames()];
		
		for (int i=0;i<fileNames.length;i++)
			fileNames[i] = Elements.fileManagement.getFile(i).getName();
		
		list.setModel(new DefaultListModel() {
			String[] values = fileNames;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource().toString().contains("invalid"))
					return;
				
				if (!e.getValueIsAdjusting()) {
					List<String> arr = Elements.fileManagement.getAllLines(Elements.fileManagement.getFile(list.getSelectedIndex()));
					
					List<String> arrFigure = new ArrayList<String>();
					List<String> arrPath = new ArrayList<String>();
					for (String i : arr) {
						if (i.contains("Figura")) {
							String buffer = "";
							
							int begin = i.lastIndexOf("(");
							int end = i.lastIndexOf(")");
							String sub = i.substring(begin + 1, end);
							arrPath.add(sub);
							
							buffer = i.replaceAll(" - predjeni put \\([0-9-]*\\) -", "");
							i.indexOf("");
							
							arrFigure.add(buffer);
						}
					}
					panelInside2.removeAll();
					generateArrPlayer(arr.stream().filter(t -> t.contains("PLAYER")).collect(Collectors.toList()));
					generateArrPanel();
					generateArrLabel(arrFigure);
					generateArrButton(arr.stream().filter(t -> t.contains("Figura")).count(), arrPath);
				}
			}
			
		});
		panel1.setLayout(new BorderLayout(0, 0));
		
		list.setFixedCellHeight(50);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		
		myPanel.add(scrollPane, BorderLayout.CENTER);
		myPanel.setPreferredSize(new Dimension(300, 500));
		panel1.add(myPanel);
		
		frmPrikazIgara.setMinimumSize(new Dimension(1000, 600));
	}
	
	private void generateArrLabel(List<String> arrLabelString) {
		for (int i=0;i<arrLabelString.size();i++) {
			arrLabel[i] = new JLabel(arrLabelString.get(i));
			arrLabel[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
			arrPanel[i].add(arrLabel[i], BorderLayout.CENTER);
		}
	}
	
	private void generateArrButton(long n, List<String> arr) {
		for (int i=0;i<n;i++) {
			arrButton[i] = new JButton("+");
			arrButton[i].setBackground(new Color(255, 255, 255));
			arrButton[i].setFont(new Font("Tahoma", Font.BOLD, 14));
			arrButton[i].setBorder(new MatteBorder(1, 1, 0, 0, (Color) new Color(0, 0, 0)));
			
			if ((i+1) % 4 == 0 && i > 0)
				arrButton[i].setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(0, 0, 0)));
			
			arrPanel[i].add(arrButton[i], BorderLayout.EAST);
			
			String value = arr.get(i);
			arrButton[i].addActionListener((ActionListener) new ActionListener() {
				@SuppressWarnings("unused")
				@Override
				public void actionPerformed(ActionEvent e) {
					CoveredPath cp = new CoveredPath(value);
				}
			});
		}
	}
	
	private void generateArrPlayer(List <String> arrPlayerName) {
		for (int i=0;i<arrRootPanel.length;i++) {
			arrRootPanel[i] = new JPanel();
			arrRootPanel[i].setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			arrRootPanel[i].setBackground(new Color(240, 240, 240, 240));
			arrRootPanel[i].setLayout(new GridLayout(5, 1));
			panelInside2.add(arrRootPanel[i]);
		}
		
		for (int i=0;i<arrPlayerName.size();i++) {
			arrPlayer[i] = new JLabel(arrPlayerName.get(i));
			arrPlayer[i].setFont(new Font("Tahoma", Font.BOLD, 12));
			arrPlayer[i].setBorder(new EmptyBorder(10, 10, 10, 10));
			arrRootPanel[i].add(arrPlayer[i]);
		}
	}
	
	private void generateArrPanel() {
		for (int i=0;i<arrPanel.length;i++) {
			arrPanel[i] = new JPanel();
			arrPanel[i].setLayout(new BorderLayout(0, 0));
			arrRootPanel[i/4].add(arrPanel[i]);
			arrRootPanel[i/4].revalidate();
			arrRootPanel[i/4].repaint();
		}
			
	}

	public void setVisible(boolean b) {
		frmPrikazIgara.setVisible(b);
	}

}
