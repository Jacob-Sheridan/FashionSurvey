import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.*;

public class FashionSurvey {
    public ArrayList<Outfit> outfits;
    public int oldMoneyScore;
    public int opiumScore;
    public int starboyScore;

    public void OutfitManager(String filePath) {
        outfits = new ArrayList<>();
        loadOutfitsFromFile(filePath);
    }

    public void addScores(int index){
        oldMoneyScore += outfits.get(index).oldMoneyValue;
        opiumScore += outfits.get(index).opiumValue;
        starboyScore += outfits.get(index).starboyValue;
    }

    public void subScores(int index){
        oldMoneyScore -= outfits.get(index).oldMoneyValue;
        opiumScore -= outfits.get(index).opiumValue;
        starboyScore -= outfits.get(index).starboyValue;
    }

    public String getFavoriteStyle(){
        if (oldMoneyScore > opiumScore && oldMoneyScore > starboyScore){
            return "Old Money";
        }
        else if (opiumScore > oldMoneyScore && opiumScore > starboyScore){
            return "Opium";
        }
        else{
            return "Starboy";
        }
    }

    private void loadOutfitsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String imageLocation;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    imageLocation = String.format("../images/%s.jpg", parts[0]);
                    outfits.add(new Outfit(imageLocation, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JTextArea resultsArea;
    public void resultsWindow() {
        JFrame frame = new JFrame("Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 800);

        // Create and set up the results area
        resultsArea = new JTextArea(20, 40); // Adjust rows and columns as needed
        resultsArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton button1 = new JButton("Close");
        panel.add(button1);

        frame.add(panel, BorderLayout.SOUTH);

        // Add action to the button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
        addToResults("Favorite Style: " + getFavoriteStyle());
    }

    // Method to add text to results
    private void addToResults(String text) {
        if (resultsArea != null) {
            resultsArea.append(text + "\n");
            resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
        }
    }

    public void drawWindow(String imagePath, int index) {
        JFrame frame = new JFrame("Fashion Survey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 800);

		// Create a panel with a button
		JPanel panel = new JPanel();
		JButton button1 = new JButton("Like it");
        JButton button2 = new JButton("Hate it");
		panel.add(button1);
        panel.add(button2);

        frame.add(new JLabel(new ImageIcon(imagePath)), BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);

		frame.setVisible(true);

		// Add action to the button
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                if (index == outfits.size() - 1){
                    addScores(index);
                    frame.dispose();
                    resultsWindow();
                }
                else{
                    addScores(index);
                    frame.dispose();
                    drawWindow(outfits.get(index + 1).imageLocation, index + 1);
                }
			}
		});
        button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                if (index == outfits.size() - 1){
                    subScores(index);
                    frame.dispose();
                    resultsWindow();
                }
                else{
                    subScores(index);
                    frame.dispose();
                    drawWindow(outfits.get(index + 1).imageLocation, index + 1);
                }
			}
		});
    }
}