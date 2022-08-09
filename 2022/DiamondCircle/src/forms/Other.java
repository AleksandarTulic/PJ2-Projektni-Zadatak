package forms;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import elements.figura.Figura;
import elements.gui.RoundButton;
import elements.igrac.Igrac;
import elements.karta.Karta;
import elements.karta.SpecijalnaKarta;
import help.Elements;
import help.Pair;
import help.Rupa;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class Other {

	private JFrame frame;
	public static JPanel [][]panelArr = new JPanel[10][10];
	public static JPanel [][]panelString = new JPanel[10][10];
	public static JLabel [][] labelTip = new JLabel[10][10]; 
	public static JLabel [][] labelDijamant = new JLabel[10][10]; 
	private JPanel panelOutside2222 = null;
	@SuppressWarnings("rawtypes")
	private static JList list = null;
	private static JLabel label10 = null;
	private static JLabel background1 = null;
	private static JPanel panelOutside2221111 = null;
	private static JPanel panelOutside2221112 = null;
	private static JLabel label8 = null;
	private static JPanel panelOutside2211 = null;
	private static JLabel[] labelPlayer = null;
	private static JPanel panelOutside11 = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Other window = new Other();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Other() {
		initialize();
	}

	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 806, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground( Color.white );
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setOpaque(true);
		panel.setBackground(Color.white);
		
		JPanel panelInside = new JPanel();
		panelInside.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(panelInside);
		panelInside.setLayout(new GridLayout(1, 5));
		panelInside.setOpaque(true);
		panelInside.setBackground(Color.white);
		
		JTextArea textArea = new JTextArea(2, 20);
		textArea.setText("Trenutni broj odigranih igara: " + Elements.fileManagement.getNumberOfGames());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		textArea.setBackground(Color.white);
		textArea.setForeground(Color.BLUE);
		textArea.setMargin(new Insets(18, 20, 2, 2));
		panelInside.add(textArea);
		
		JLabel empty1 = new JLabel("");
		panelInside.add(empty1);
		
		JLabel label2 = new JLabel("DiamondCircle", SwingConstants.CENTER);
		label2.setBackground(new Color(255, 228, 181));
		label2.setFont(new Font("MV Boli", Font.BOLD, 18));
		label2.setForeground(Color.RED);
		label2.setOpaque(true);
		label2.setBorder(BorderFactory.createLineBorder(new Color(255, 240, 199), 3));
		panelInside.add(label2);
		
		JLabel empty2 = new JLabel("");
		panelInside.add(empty2);
		
		JButton button1 = new JButton("Pokreni / Zaustavi");
		button1.setBorder(new RoundButton(20));
		button1.setBackground(Color.white);
		button1.setOpaque(true);
		button1.setPreferredSize(new Dimension(40, 40));
		button1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button1.setForeground(Color.BLUE);
		panelInside.add(button1);
		
		/*
		 * ***********************************************************************************
		 * STARTING GAME OR CONTINUING **********************************************************************
		 * ***********************************************************************************
		 * */
		button1.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Elements.flagGame == 0) {
					JTextField playerField = new JTextField(5);
				    JTextField rowField = new JTextField(5);
				    JTextField columnField = new JTextField(5);
	
					JPanel myPanel = new JPanel();
					myPanel.add(new JLabel("Broj Igraca:"));
					myPanel.add(playerField);
					myPanel.add(Box.createHorizontalStrut(15)); // a spacer
					myPanel.add(new JLabel("Broj Redova:"));
					myPanel.add(rowField);
					myPanel.add(new JLabel("Broj Kolona:"));
					myPanel.add(columnField);
					
					playerField.addKeyListener((KeyListener) new KeyListener() {
						@Override
						public void keyTyped(KeyEvent e) {
							char c = e.getKeyChar();
					        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					            e.consume();  // if it's not a number, ignore the event
					        }
						}
	
						@Override
						public void keyPressed(KeyEvent e) {
						}
	
						@Override
						public void keyReleased(KeyEvent e) {
						}
					});
					
					rowField.addKeyListener((KeyListener) new KeyListener() {
						@Override
						public void keyTyped(KeyEvent e) {
							char c = e.getKeyChar();
					        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					            e.consume();  // if it's not a number, ignore the event
					        }
						}
	
						@Override
						public void keyPressed(KeyEvent e) {
						}
	
						@Override
						public void keyReleased(KeyEvent e) {
						}
					});
					
					columnField.addKeyListener((KeyListener) new KeyListener() {
						@Override
						public void keyTyped(KeyEvent e) {
							char c = e.getKeyChar();
					        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					            e.consume();  // if it's not a number, ignore the event
					        }
						}
	
						@Override
						public void keyPressed(KeyEvent e) {
						}
	
						@Override
						public void keyReleased(KeyEvent e) {
						}
					});
					
					int result = JOptionPane.showConfirmDialog(null, myPanel, "", JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						int playerNum = -1;
						int row = -1;
						int col = -1;
						
						try {
							playerNum = Integer.parseInt(playerField.getText());
							row = Integer.parseInt(rowField.getText());
							col = Integer.parseInt(columnField.getText());
						}catch (Exception ee) {
							ee.printStackTrace();
						}
						
						if (playerNum >= Elements.MIN_PLAYERS && playerNum <= Elements.MAX_PLAYERS && 
								row >= Elements.MIN_ROW && row <= Elements.MAX_ROW &&
								col >= Elements.MIN_COLUMN && col <= Elements.MAX_COLUMN) {
							panelOutside2222.removeAll();
							panelOutside2222.setLayout(new GridLayout(row, col));
							panelOutside2222.setBorder(new EmptyBorder(30, 30, 30, 30));
							panelOutside2222.setBackground(new Color(255, 255, 255));
							
							int br = 1;
							for (int i=0;i<row;i++) {
								for (int j=0;j<col;j++) {
									panelArr[i][j] = new JPanel();
									panelOutside2222.add(panelArr[i][j]);
									panelArr[i][j].setBorder(new LineBorder(new Color(128, 128, 128)));
									panelArr[i][j].setBackground(new Color(255, 255, 255));
									
									panelArr[i][j].setLayout(new BorderLayout(0, 0));
									JLabel labelValue = new JLabel("" + br++);
									labelValue.setHorizontalAlignment(JLabel.CENTER);
									labelValue.setFont(new Font("Tahoma", Font.BOLD, 12));
									panelArr[i][j].add(labelValue, BorderLayout.CENTER);
									
									panelString[i][j] = new JPanel();
									panelString[i][j].setBorder(new EmptyBorder(5, 5, 5, 5));
									panelString[i][j].setLayout(new GridLayout(1, 2));
									
									labelTip[i][j] = new JLabel("");
									labelTip[i][j].setHorizontalAlignment(JLabel.CENTER);
									
									labelDijamant[i][j] = new JLabel("0");
									labelDijamant[i][j].setHorizontalAlignment(JLabel.CENTER);
									
									panelString[i][j].add(labelTip[i][j]);
									panelString[i][j].add(labelDijamant[i][j]);
									panelString[i][j].setBackground(Color.WHITE);
									
									panelArr[i][j].add(panelString[i][j], BorderLayout.NORTH);
								}
							}
							
							Elements.flagGame = 1;
							Elements.init(row, col, playerNum);
							
							labelPlayer = new JLabel[playerNum * 2 - 1]; //zbog razmaka
							panelOutside11.setLayout(new GridLayout(1, labelPlayer.length));
							for (int i=0;i<labelPlayer.length;i++) {
								labelPlayer[i] = new JLabel("");
								labelPlayer[i].setBackground(Color.WHITE);
							}
							
							br = 0;
							for (int i=0;i<labelPlayer.length;i+=2) {
								labelPlayer[i].setText(Elements.arrIgrac.get(br).getIme());
								labelPlayer[i].setForeground(Elements.arrIgrac.get(br).getFigure()[0].getColor());
								labelPlayer[i].setFont(new Font("Tahoma", Font.BOLD, 12));
								labelPlayer[i].setHorizontalAlignment(SwingConstants.CENTER);
								panelOutside11.add(labelPlayer[i]);
								
								if (i < labelPlayer.length - 1)
									panelOutside11.add(labelPlayer[i + 1]);
								br++;
							}
						}else {
							JOptionPane.showMessageDialog(null, "Vrijednosti unesene nisu u odgovrajucem range-u.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}else {
					if (Elements.flagGame % 2 == 0)
						for (Igrac i : Elements.arrIgrac)
							i.pozovi();
					
					Elements.flagGame++;
				}
			}
			
		});
		
		JPanel panelOutside = new JPanel();
		panelOutside.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panelOutside, BorderLayout.CENTER);
		panelOutside.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside1 = new JPanel();
		panelOutside1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelOutside1.setBackground(new Color(255, 255, 255));
		panelOutside.add(panelOutside1, BorderLayout.NORTH);
		panelOutside1.setLayout(new BorderLayout(0, 0));
		
		panelOutside11 = new JPanel();
		panelOutside11.setBackground(new Color(255, 255, 255));
		panelOutside11.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside1.add(panelOutside11);
		panelOutside11.setLayout(new GridLayout(1, 5));
		
		JPanel panelOutside2 = new JPanel();
		panelOutside2.setBackground(new Color(255, 255, 255));
		panelOutside2.setBorder(new EmptyBorder(10, 0, 0, 0));
		panelOutside.add(panelOutside2, BorderLayout.CENTER);
		panelOutside2.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside21 = new JPanel();
		panelOutside21.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelOutside21.setBackground(new Color(255, 255, 255));
		panelOutside2.add(panelOutside21, BorderLayout.WEST);
		panelOutside21.setPreferredSize(new Dimension(150, 400));
		panelOutside21.setLayout(new BorderLayout(0, 0));
		
		list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new DefaultListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		/*
		 * ***********************************************************************************
		 * COVERED PATH **********************************************************************
		 * ***********************************************************************************
		 * */
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource().toString().contains("invalid"))
					return;
				
				if (!e.getValueIsAdjusting()) {
					@SuppressWarnings("unused")
					CoveredPath coveredPath = new CoveredPath(Elements.arrIgrac.get(list.getSelectedIndex() / Elements.NUMBER_OF_FIGURA).getFigure()[list.getSelectedIndex() % Elements.NUMBER_OF_FIGURA]);
				}
			}
			
		});
		
		list.setFixedCellHeight(50);
		list.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		panelOutside21.add(scrollPane);
		
		JPanel panelOutside22 = new JPanel();
		panelOutside22.setBackground(new Color(255, 255, 255));
		panelOutside2.add(panelOutside22, BorderLayout.CENTER);
		panelOutside22.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside221 = new JPanel();
		panelOutside221.setBorder(new EmptyBorder(10, 10, 0, 10));
		panelOutside221.setBackground(new Color(255, 255, 255));
		panelOutside22.add(panelOutside221, BorderLayout.SOUTH);
		panelOutside221.setLayout(new BorderLayout(0, 0));
		
		panelOutside2211 = new JPanel();
		panelOutside2211.setBackground(new Color(255, 255, 255));
		panelOutside2211.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelOutside221.add(panelOutside2211, BorderLayout.CENTER);
		panelOutside2211.setLayout(new GridLayout(3, 1));
		
		JLabel label7 = new JLabel("Opis znacenja karti:");
		label7.setHorizontalAlignment(SwingConstants.CENTER);
		label7.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelOutside2211.add(label7);
		
		JLabel empty6 = new JLabel("");
		panelOutside2211.add(empty6);
		
		label8 = new JLabel("Na potezu je igrac 2, Figura 3, prelazi 3 polja, pomjera se sa pozicije 4 na 28.");
		label8.setHorizontalAlignment(SwingConstants.CENTER);
		label8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelOutside2211.add(label8);
		label8.setVisible(false);
		
		JPanel panelOutside2212 = new JPanel();
		panelOutside2212.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelOutside2212.setBackground(new Color(255, 255, 255));
		panelOutside221.add(panelOutside2212, BorderLayout.EAST);
		panelOutside2212.setLayout(new GridLayout(1, 1));
		
		JPanel panelOutside22121 = new JPanel();
		panelOutside22121.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelOutside22121.setBackground(new Color(255, 255, 255));
		panelOutside2212.add(panelOutside22121);
		panelOutside22121.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelOutside221211 = new JPanel();
		panelOutside221211.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside221211.setBackground(new Color(255, 255, 255));
		panelOutside22121.add(panelOutside221211);
		
		JButton button2 = new JButton("Prikaz liste fajlova \r\nsa rezultatima");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ShowGamesPlayed sgp = new ShowGamesPlayed();
				Show show = new Show(frame);
				show.setVisible(true);
				frame.setEnabled(false);
			}
		});
		button2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button2.setBackground(new Color(255, 255, 255));
		button2.setBorder(new RoundButton(20));
		button2.setMaximumSize(new Dimension(100, 100));
		panelOutside221211.add(button2);
		
		JPanel panelOutside222 = new JPanel();
		panelOutside222.setBorder(new EmptyBorder(10, 10, 0, 10));
		panelOutside222.setBackground(new Color(255, 255, 255));
		panelOutside22.add(panelOutside222, BorderLayout.CENTER);
		panelOutside222.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside2221 = new JPanel();
		panelOutside2221.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside2221.setBackground(new Color(255, 255, 255));
		panelOutside222.add(panelOutside2221, BorderLayout.EAST);
		panelOutside2221.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside22211 = new JPanel();
		panelOutside22211.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelOutside22211.setBackground(new Color(255, 255, 255));
		panelOutside2221.add(panelOutside22211, BorderLayout.CENTER);
		panelOutside22211.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside222111 = new JPanel();
		panelOutside222111.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside222111.setBackground(new Color(255, 255, 255));
		panelOutside22211.add(panelOutside222111, BorderLayout.CENTER);
		panelOutside222111.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOutside222111Dodatno = new JPanel();
		panelOutside222111Dodatno.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelOutside222111Dodatno.setBackground(new Color(255, 255, 255));
		panelOutside222111.add(panelOutside222111Dodatno, BorderLayout.CENTER);
		panelOutside222111Dodatno.setLayout(new BorderLayout(0, 0));
		
		panelOutside2221111 = new JPanel();
		panelOutside2221111.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside2221111.setBackground(new Color(255, 255, 255));
		panelOutside222111Dodatno.add(panelOutside2221111, BorderLayout.CENTER);
		panelOutside2221111.setMaximumSize(new Dimension(250, 250));
		panelOutside2221111.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + File.separator + "images" + File.separator + "diamond.png");
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		background1 = new JLabel(imageIcon);
		background1.setMaximumSize(new Dimension(250, 250));
		background1.setMinimumSize(new Dimension(250, 250));
		background1.setPreferredSize(new Dimension(250, 250));
		panelOutside2221111.add(background1);
		
		panelOutside2221112 = new JPanel();
		panelOutside2221112.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelOutside2221112.setBackground(new Color(255, 255, 255));
		panelOutside222111Dodatno.add(panelOutside2221112, BorderLayout.SOUTH);
		panelOutside2221112.setLayout(new GridLayout(1, 5));
		
		JPanel color1 = new JPanel();
		color1.setBorder(new EmptyBorder(15, 0, 10, 0));
		color1.setBackground(new Color(255, 228, 181));
		panelOutside2221112.add(color1);
		
		JPanel empty7 = new JPanel();
		empty7.setBackground(new Color(255, 255, 255));
		panelOutside2221112.add(empty7);
		
		JPanel color2 = new JPanel();
		color2.setBackground(new Color(255, 228, 181));
		panelOutside2221112.add(color2);
		
		JPanel empty8 = new JPanel();
		empty8.setBackground(new Color(255, 255, 255));
		panelOutside2221112.add(empty8);
		
		JPanel color3 = new JPanel();
		color3.setBackground(new Color(255, 228, 181));
		panelOutside2221112.add(color3);
		
		JPanel trenutnaKarta = new JPanel();
		trenutnaKarta.setBorder(new EmptyBorder(10, 10, 10, 10));
		trenutnaKarta.setBackground(new Color(255, 255, 255));
		panelOutside222111.add(trenutnaKarta, BorderLayout.NORTH);
		
		JLabel label9 = new JLabel("Trenutna Karta");
		label9.setHorizontalAlignment(SwingConstants.CENTER);
		label9.setOpaque(true);
		label9.setFont(new Font("Tahoma", Font.BOLD, 12));
		label9.setBackground(new Color(135, 206, 250));
		label9.setBorder(new EmptyBorder(10, 30, 10, 30));
		trenutnaKarta.add(label9, BorderLayout.NORTH);
		
		JPanel panelOutside22212 = new JPanel();
		panelOutside22212.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelOutside22212.setBackground(new Color(255, 255, 255));
		panelOutside2221.add(panelOutside22212, BorderLayout.SOUTH);
		
		label10 = new JLabel("Vrijeme Trajanja Igre: 10s");
		label10.setBackground(new Color(255, 228, 181));
		label10.setFont(new Font("MV Boli", Font.PLAIN, 14));
		panelOutside22212.add(label10);
		label10.setOpaque(true);
		label10.setBorder(BorderFactory.createLineBorder(new Color(255, 240, 199), 2));
		label10.setVisible(false);
		
		panelOutside2222 = new JPanel();
		panelOutside2222.setBorder(new EmptyBorder(30, 30, 30, 30));
		panelOutside2222.setBackground(new Color(255, 255, 255));
		panelOutside222.add(panelOutside2222, BorderLayout.CENTER);
		panelOutside2222.setLayout(new GridLayout(7, 7));
	}
	
	public static void changeGameTime(long seconds) {
		label10.setVisible(true);
		label10.setText("Vrijeme Trajanja Igre: " + seconds);
	}
	
	public static void changeImageIcon(Karta karta, Figura figura, int startPosition, int endPosition) {
		ImageIcon imageIcon = new ImageIcon(karta.getImagePath());
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		background1 = new JLabel(imageIcon);
		background1.setMaximumSize(new Dimension(250, 250));
		background1.setMinimumSize(new Dimension(250, 250));
		background1.setPreferredSize(new Dimension(250, 250));
		panelOutside2221111.removeAll();
		panelOutside2221111.add(background1);
		panelOutside2221111.revalidate();
		panelOutside2221111.repaint();
		
		label8.setVisible(true);
		if (karta instanceof SpecijalnaKarta) {
			panelOutside2221112.setVisible(false);
			
			List<Integer> arrRupa = new ArrayList<Integer>();
			
			Random rand = new Random();
			for (int i=0;i<karta.getNum();i++)
			{
				int value = -1;
				boolean flag = true;
				
				do {
					value = rand.nextInt(Elements.putanja.getLength());
					Pair element = Elements.putanja.getIndex(value);
					
					if (Elements.map[element.getX()][element.getY()] == null || Elements.map[element.getX()][element.getY()] instanceof Rupa)
						flag = false;
				}while(arrRupa.contains(value) || flag);
			
				arrRupa.add(value);
			}
			
			List<Pair> arrPutanja = Elements.putanja.getArrPutanja();
			for (Integer i : arrRupa) {
				Other.panelArr[arrPutanja.get(i).getX()][arrPutanja.get(i).getY()].setBackground(Color.BLACK);
				Elements.map[arrPutanja.get(i).getX()][arrPutanja.get(i).getY()] = new Rupa();
			}
			
			String playerName = "";
			int redFigure = -1;
			
			for (Igrac i : Elements.arrIgrac) {
				Figura []figure = i.getFigure();
				
				redFigure = Arrays.stream(figure).collect(Collectors.toList()).indexOf(figura);
				
				if (redFigure != -1) {
					playerName = i.getIme();
					break;
				}
			}
			
			label8.setText("Na potezu je igrac " + playerName + ", Figura " + redFigure + "Rupe se postavljaju na " + karta.getNum() + " polja.");
		}else {
			panelOutside2221112.removeAll();
			JPanel arrEmpty[] = new JPanel[karta.getNum() - 1];
			JPanel arrSteps[] = new JPanel[karta.getNum()];
			
			for (int i=0;i<arrEmpty.length;i++) {
				arrEmpty[i] = new JPanel();
				arrEmpty[i].setBackground(new Color(255, 255, 255));
				
				arrSteps[i] = new JPanel();
				arrSteps[i].setBorder(new EmptyBorder(15, 0, 10, 0));
				arrSteps[i].setBackground(new Color(255, 228, 181));
				panelOutside2221112.add(arrSteps[i]);
				panelOutside2221112.add(arrEmpty[i]);
			}
			
			arrSteps[karta.getNum() - 1] = new JPanel();
			arrSteps[karta.getNum() - 1].setBorder(new EmptyBorder(15, 0, 10, 0));
			arrSteps[karta.getNum() - 1].setBackground(new Color(255, 228, 181));
			panelOutside2221112.add(arrSteps[karta.getNum() - 1]);
			panelOutside2221112.revalidate();
			panelOutside2221112.repaint();
			panelOutside2221112.setVisible(true);
			
			String playerName = "";
			int redFigure = -1;
			
			for (Igrac i : Elements.arrIgrac) {
				Figura []figure = i.getFigure();
				
				redFigure = Arrays.stream(figure).collect(Collectors.toList()).indexOf(figura);
				
				if (redFigure != -1) {
					playerName = i.getIme();
					break;
				}
			}
			
			label8.setText("Na potezu je igrac " + playerName + ", Figura " + redFigure + ", Prelazi " + karta.getNum() + " polja, pomjera se sa pozicije " + startPosition + " na " + endPosition);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public static void setListModel() {
		String []arrNames = new String[Elements.arrIgrac.size() * Elements.NUMBER_OF_FIGURA];
		for (int i=0;i<Elements.arrIgrac.size() * Elements.NUMBER_OF_FIGURA;i++)
			arrNames[i] = "Figura " + (i + 1);
		
		list.setModel(new DefaultListModel() {
			String[] values = arrNames;
			
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}

}
