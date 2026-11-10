package com.game2048.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.game2048.model.Direction;

public class SwingNoAnimView extends JFrame implements GameView {

  private final LinkedBlockingQueue<Direction> directionQueue = new LinkedBlockingQueue<>();
  private JPanel mainPanel = new JPanel(new BorderLayout());
  private JLabel scoreLabel = new JLabel();
  private JLabel[][] cellsLabels;

  public SwingNoAnimView() {
    super();
  }

  @Override
  public void displayGame(int size) {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;

    setTitle("2048-Game");
    setSize(width/3, width/3);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
          Direction dir = switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> Direction.LEFT;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            case KeyEvent.VK_UP    -> Direction.UP;
            case KeyEvent.VK_DOWN  -> Direction.DOWN;
            default -> null;
          };
          if (dir != null) {
            directionQueue.offer(dir);
          }
        }
      });
    
    setContentPane(mainPanel);
    
    JPanel scorePanel = new JPanel();
    mainPanel.add(scorePanel, BorderLayout.NORTH);

    JPanel gridPanel = new JPanel(new GridLayout(size, size, 0, 0));
    gridPanel.setBorder(new EmptyBorder(35, 60, 35, 60));
    mainPanel.add(gridPanel, BorderLayout.CENTER);

    cellsLabels = new JLabel[size][size];
    for (int i=0 ; i<size ; i++) {
      for (int j=0 ; j<size ; j++) {
        cellsLabels[i][j] = new JLabel("", SwingConstants.CENTER);
        cellsLabels[i][j].setFont(new Font("Arial", Font.BOLD, 24));
        cellsLabels[i][j].setBorder(BorderFactory.createLineBorder(new Color(187, 173, 160), 2));
        gridPanel.add(cellsLabels[i][j]);
      }
    }
    scorePanel.add(scoreLabel);

    gridPanel.setBackground(new Color(250, 248, 239));
    gridPanel.setOpaque(true);
    setVisible(true);
  }

  @Override
  public void displayGrid(int[][] cells, int size) {
    SwingUtilities.invokeLater(() -> {
      for (int i=0 ; i<size ; i++) {
        for (int j=0 ; j<size ; j++) {
          if (cells[i][j] == 0) {
            cellsLabels[i][j].setText("");
          } else {
            cellsLabels[i][j].setText(String.valueOf(cells[i][j]));
          }
          cellsLabels[i][j].setBackground(findBackgroundColor(cells[i][j]));
          cellsLabels[i][j].setForeground(findTextColor(cells[i][j]));
          cellsLabels[i][j].setOpaque(true);
        }
      }
    });
  }

  @Override
  public void displayScore(int score) {
    SwingUtilities.invokeLater(() -> {
      scoreLabel.setText("Score : " + score);
      revalidate();
      repaint();
    });
  }

  @Override
  public void displayWin() {
    SwingUtilities.invokeLater(() -> {
      JPanel winPanel = new JPanel();
      JLabel winLabel = new JLabel("GG! The game is won!");
      winPanel.setBackground(new Color(255, 255, 255, 150));
      winLabel.setFont(new Font("Arial", Font.BOLD, 72));
      winPanel.add(winLabel);
      mainPanel.add(winPanel);
      winPanel.setVisible(true);
      mainPanel.setVisible(true);
    });
  }

  @Override
  public void displayLose() {
    SwingUtilities.invokeLater(() -> {
      JOptionPane.showMessageDialog(this, "OH no... The game is lost.", "Lose Window", JOptionPane.INFORMATION_MESSAGE);
    });
  }

  @Override
  public Direction getDirection() {
    try {
      return directionQueue.take();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return null;
    }
  }

  private Color findBackgroundColor(int nb) {
    switch (nb) {
      case 0:    return new Color(205, 193, 180);
      case 2:    return new Color(238, 228, 218);
      case 4:    return new Color(237, 224, 200);
      case 8:    return new Color(242, 177, 121);
      case 16:   return new Color(245, 149, 99);
      case 32:   return new Color(246, 124, 95);
      case 64:   return new Color(246, 94, 59);
      case 128:  return new Color(237, 207, 114);
      case 256:  return new Color(237, 204, 97);
      case 512:  return new Color(237, 200, 80);
      case 1024: return new Color(237, 197, 63);
      case 2048: return new Color(237, 194, 46);
      default:   return new Color(60, 58, 50);
    }
  }

  private Color findTextColor(int nb) {
    switch (nb) {
      case 2, 4: return new Color(119, 110, 101);
      default:   return new Color(250, 250, 250);
    }
  }
    
}