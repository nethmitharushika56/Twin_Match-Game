package view;

import javax.swing.*;

import model.HighScoreManager;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Dialog to display high scores
 */
public class HighScoreDialog extends JDialog {
    
    public HighScoreDialog(JFrame parent, GameLevel level) {
        super(parent, "High Scores - " + level.getDisplayName(), true);
        initializeDialog(level);
    }
    
    private void initializeDialog(GameLevel level) {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("High Scores - " + level.getDisplayName());
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(70, 130, 180));
        
        // High scores table
        List<HighScoreManager.HighScore> scores = HighScoreManager.getHighScores((model.GameLevel) level);
        String[] columnNames = {"Rank", "Player", "Score", "Attempts", "Date"};
        Object[][] data = new Object[scores.size()][5];
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (int i = 0; i < scores.size(); i++) {
            HighScoreManager.HighScore score = scores.get(i);
            data[i][0] = "#" + (i + 1);
            data[i][1] = score.getPlayerName();
            data[i][2] = score.getScore();
            data[i][3] = score.getAttempts();
            data[i][4] = sdf.format(new Date(score.getDate()));
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);
        table.setEnabled(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(100, 149, 237));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
} 