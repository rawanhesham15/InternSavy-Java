import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.Scanner;

public class TicTacToe extends JPanel implements ActionListener {

    // logic variables
    boolean playerX;
    boolean gameDone = false;
    int winner = -1;
    int player1wins = 0, player2wins = 0;
    int[][] board = new int[3][3];

    // paint variables
    int lineWidth = 5;
    int lineLength = 270;
    int x = 15, y = 100; // location of first line.
    int offset = 95; // square width.
    int a = 0;
    int b = 5;
    int selX = 0, selY = 0;

    // Colors
    Color turtle = new Color(0x80bdab);
    Color orange = new Color(0xfdcb9e);
    Color offWhite = new Color(0xf7f7f7);
    Color darkGray = new Color(0x3f3f44);

    // Components
    JButton jButton;

    // constructor
    public TicTacToe() {
        Dimension size = new Dimension(420, 300);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        jButton = new JButton("Play Agian");
        jButton.addActionListener(this);
        // jButton.setVisible(false);
        resetGame();
        add(jButton);
        jButton.setVisible(false);
        addMouseListener(new XOListener());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.getContentPane();

        TicTacToe tPanel = new TicTacToe();
        frame.add(tPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
        repaint(); // Redraw the game board to clear the previous moves
        getJButton().setVisible(false); // Hide the "Play Again" button
    }

    public JButton getJButton() {
        return jButton;
    }

    public void setPlayer1Wins(int a) {
        player1wins = a;
    }

    public void setPlayer2Wins(int a) {
        player2wins = a;
    }

    public void resetGame() {
        playerX = true;
        winner = -1;
        gameDone = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        getJButton().setVisible(false);
    }

    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        drawBoard(page);
        drawUi(page);
        drawGame(page);
    }

    public void drawBoard(Graphics page) {
        setBackground(turtle);
        page.setColor(darkGray);
        page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30);
        page.fillRoundRect(x, y + offset, lineLength, lineWidth, 5, 30);
        page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);
        page.fillRoundRect(y + offset, x, lineWidth, lineLength, 30, 5);
    }

    public void drawUi(Graphics page) {
        // set color and font
        page.setColor(darkGray);
        page.fillRect(300, 0, 120, 300);
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        page.setFont(font);

        // set winner counter
        page.setColor(offWhite);
        page.drawString("Win Count", 310, 30);
        page.drawString(": " + player1wins, 362, 70);
        page.drawString(": " + player2wins, 362, 105);

        // draw score x
        ImageIcon xIcon = new ImageIcon("orangex.png");
        Image xImg = xIcon.getImage();
        Image newXImage = xImg.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newXIcon = new ImageIcon(newXImage);
        page.drawImage(newXIcon.getImage(), 329, 47, null);

        // draw score O
        page.setColor(offWhite);
        page.fillOval(328, 80, 30, 30);
        page.setColor(darkGray);
        page.fillOval(334, 85, 19, 19);

        // draw whos turn or winner
        page.setColor(offWhite);
        Font font1 = new Font("Serif", Font.ITALIC, 18);
        page.setFont(font1);

        if (gameDone) {
            // show winner
            if (winner == 1) {
                page.drawString("The Winner is: ", 310, 150);
                page.drawImage(xImg, 335, 160, null);
            } else if (winner == 2) {
                page.drawString("The Winner is: ", 310, 150);
                page.setColor(offWhite);
                page.fillOval(332, 160, 50, 50);
                page.setColor(darkGray);
                page.fillOval(343, 170, 30, 30);
            } else if (winner == 3) {
                // tie
                page.drawString("It's a Tie", 330, 178);
            }
        } else {
            // show whos turn
            Font font2 = new Font("Serif", Font.ITALIC, 20);
            page.setFont(font2);
            page.drawString("It's", 350, 160);
            if (playerX) {
                page.drawString("X's turn", 325, 180);
            } else {
                page.drawString("O's turn", 325, 180);
            }
        }
    }

    public void drawGame(Graphics page) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    // do nothing
                } else if (board[i][j] == 1) {
                    ImageIcon xIcon = new ImageIcon("orangex.png", null);
                    Image xImg = xIcon.getImage();
                    page.drawImage(xImg, 30 + offset * i, 30 + offset * j, null);
                } else if (board[i][j] == 2) {
                    page.setColor(offWhite);
                    page.fillOval(30 + offset * i, 30 + offset * j, 50, 50);
                    page.setColor(turtle);
                    page.fillOval(40 + offset * i, 40 + offset * j, 30, 30);
                }
            }
        }
    }

    public class XOListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            if (!gameDone) {
                a = event.getX();
                b = event.getY();

                // Update selX and selY directly from the main class
                if (a > 12 && a < 99) {
                    selX = 0;
                } else if (a > 103 && a < 195) {
                    selX = 1;
                } else if (a > 200 && a < 287) {
                    selX = 2;
                } else {
                    selX = -1;
                }

                if (b > 12 && b < 99) {
                    selY = 0;
                } else if (b > 103 && b < 195) {
                    selY = 1;
                } else if (b > 200 && b < 287) {
                    selY = 2;
                } else {
                    selY = -1;
                }

                if (selX != -1 && selY != -1) {
                    if (board[selX][selY] == 0) {
                        if (playerX) {
                            board[selX][selY] = 1;
                            playerX = false;
                        } else {
                            board[selX][selY] = 2;
                            playerX = true;
                        }
                        checkWinner();
                        repaint(); // You should call repaint to update the game display.
                    }
                } else {
                    System.out.println("Invalid click");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public void checkWinner() {
        if (gameDone) {
            System.out.println("Game Done!");
            return;
        }

        // vertical
        int temp = -1;
        if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != 0) {
            temp = board[0][0];
        } else if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != 0) {
            temp = board[1][0];
        } else if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != 0) {
            temp = board[2][0];
        }

        // horizontal
        else if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != 0) {
            temp = board[0][0];
        } else if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != 0) {
            temp = board[0][1];
        } else if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != 0) {
            temp = board[0][2];
        }

        // diagonal
        else if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            temp = board[0][0];
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            temp = board[0][2];
        } else {
            // Check for a tie when no winner is found
            boolean notDone = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        notDone = true;
                        break;
                    }
                }
            }
            if (notDone == false) {
                temp = 3;
            }
        }
        if (temp > 0) {
            winner = temp;
            if (winner == 1) {
                player1wins++;
                System.out.println("x wins");
            } else if (winner == 2) {
                player2wins++;
                System.out.println("o wins");
            } else if (winner == 3) {
                System.out.println("tie");
            }
            gameDone = true;
            getJButton().setVisible(true);
        }
    }
}