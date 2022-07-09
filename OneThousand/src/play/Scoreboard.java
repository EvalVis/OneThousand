package play;

import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import utils.Misc;

public class Scoreboard extends JFrame {
	
	private List<Integer> stats = new ArrayList<Integer>();
	private List<Integer> stats1 = new ArrayList<Integer>();
	private List<Integer> stats2 = new ArrayList<Integer>();
	
	private JList<Integer> list;
	private JList<Integer> list1;
	private JList<Integer> list2;
	
	private String name;
	private String name1;
	private String name2;
	
	private JLabel nameAndScore;
	private JLabel nameAndScore1;
	private JLabel nameAndScore2;

	private static final long serialVersionUID = 1L;
	
	public Scoreboard() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(400, 600);
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		nameAndScore = new JLabel();
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		getContentPane().add(nameAndScore, gbc_label);
		
		nameAndScore1 = new JLabel();
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(nameAndScore1, gbc_lblNewLabel);
		
		nameAndScore2 = new JLabel();
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 1;
		getContentPane().add(nameAndScore2, gbc_lblNewLabel_1);
		
		list = new JList<Integer>();
		GridBagConstraints gbc_list1 = new GridBagConstraints();
		gbc_list1.insets = new Insets(0, 0, 0, 5);
		gbc_list1.fill = GridBagConstraints.BOTH;
		gbc_list1.gridx = 2;
		gbc_list1.gridy = 2;
		getContentPane().add(list, gbc_list1);
		
		list1 = new JList<Integer>();
		GridBagConstraints gbc_list2 = new GridBagConstraints();
		gbc_list2.fill = GridBagConstraints.BOTH;
		gbc_list2.gridx = 3;
		gbc_list2.gridy = 2;
		getContentPane().add(list1, gbc_list2);
		
		list2 = new JList<Integer>();
		GridBagConstraints gbc_list_2 = new GridBagConstraints();
		gbc_list_2.insets = new Insets(0, 0, 0, 5);
		gbc_list_2.fill = GridBagConstraints.BOTH;
		gbc_list_2.gridx = 4;
		gbc_list_2.gridy = 2;
		getContentPane().add(list2, gbc_list_2);
		setTitle("Scores");
		setResizable(false);
		setVisible(false);
	}
	
	
	public void addNames(String[] names) {
		name = names[0];
		name1 = names[1];
		name2 = names[2];
		nameAndScore.setText(name);
		nameAndScore1.setText(name1);
		nameAndScore2.setText(name2);
	}
	
	public void addScores(int[] scores) {
		stats.add(scores[0]);
		stats1.add(scores[1]);
		stats2.add(scores[2]);
		list.setListData(Misc.ListToArray(stats));
		list1.setListData(Misc.ListToArray(stats1));
		list2.setListData(Misc.ListToArray(stats2));
	}
	
	public void updateSumScore() {
		int sumScore = 0;
		for(int i = 0; i < stats.size(); i++) sumScore += stats.get(i);
		int sumScore1 = 0;
		for(int i = 0; i < stats1.size(); i++) sumScore1 += stats1.get(i);
		int sumScore2 = 0;
		for(int i = 0; i < stats2.size(); i++) sumScore2 += stats2.get(i);
		nameAndScore.setText(name + " (" + sumScore + ")");
		nameAndScore1.setText(name1 + "(" + sumScore1 + ")");
		nameAndScore2.setText(name2 + "(" + sumScore2 + ")");
	}
	
	public void activate() {
		setVisible(true);
	}
	
	public BufferedImage saveFrame() {
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		this.printAll(g2d);
		g2d.dispose();
		return image;
	}

}
