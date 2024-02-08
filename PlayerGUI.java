import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.image.BufferedImage;

public class PlayerGUI extends JFrame {
    private JPanel gridPanel, scorePanel;
    private final int gridSize = 7; // 7x7 Grid
    private JLabel[][] grid;
    private int currentRow = 0;
    private int currentCol = 0;
    private int targetRow, targetCol;
    private int score = 0;
    private ImageIcon icon, targetIcon;
    private JLabel scoreLabel;

    public PlayerGUI() {
        // Load the player picture
        icon = new ImageIcon("../img/minion.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(75, 85, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        // Create a target icon
        targetIcon = createTargetIcon();

        setTitle("Movable Picture Game");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gridPanel = new JPanel(new GridLayout(gridSize, gridSize));
        grid = new JLabel[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid[row][col] = new JLabel();
                grid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(grid[row][col]);
            }
        }

        // Initialize target position and place the target
        placeTarget();

        // Place the picture in the first square
        grid[currentRow][currentCol].setIcon(icon);

        // Add the score panel
        scorePanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel);
        
        add(gridPanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePicture(e.getKeyCode());
                checkTarget();
            }
        });
        setFocusable(true);
    }

    private ImageIcon createTargetIcon() {
        BufferedImage targetImage = new BufferedImage(75, 85, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = targetImage.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, 75, 85);
        g2d.dispose();
        return new ImageIcon(targetImage);
    }

    private void placeTarget() {
        Random rand = new Random();
        do {
            targetRow = rand.nextInt(gridSize);
            targetCol = rand.nextInt(gridSize);
        } while (targetRow == currentRow && targetCol == currentCol); // Ensure target isn't placed where the player is
        
        grid[targetRow][targetCol].setIcon(targetIcon);
    }

    private void movePicture(int keyCode) {
        int prevRow = currentRow;
        int prevCol = currentCol;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (currentRow > 0) currentRow--;
                break;
            case KeyEvent.VK_DOWN:
                if (currentRow < gridSize - 1) currentRow++;
                break;
            case KeyEvent.VK_LEFT:
                if (currentCol > 0) currentCol--;
                break;
            case KeyEvent.VK_RIGHT:
                if (currentCol < gridSize - 1) currentCol++;
                break;
        }

        // Update player position
        if (prevRow != currentRow || prevCol != currentCol) { // If position changed
            grid[prevRow][prevCol].setIcon(null);
            grid[currentRow][currentCol].setIcon(icon);
        }
    }

    private void checkTarget() {
        if (currentRow == targetRow && currentCol == targetCol) {
            score++;
            scoreLabel.setText("Score: " + score);
            if (score > 5) {
                endGame();
            } else {
            placeTarget(); // Place the target at a new random position
            }
        }
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "you win the game", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerGUI().setVisible(true));
    }
}

