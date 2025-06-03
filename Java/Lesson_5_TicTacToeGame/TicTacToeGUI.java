package Lesson_5_TicTacToeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToeGUI extends JFrame implements ActionListener {

    //Total move cnt (determine if there is a draw)
    private int xScore, oScore, moveCounter;

    //flag indicate the player is X or not
    private boolean isPlayerOne;

    private JLabel turnLabel, scoreLabel, resultLabel;
    private JButton board[][];
    private JDialog resultDialog;

    TicTacToeGUI(){
        super("Tic Tac Toe");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        //init variable
        createResultDialog();
        board = new JButton[3][3];

        //X-start first
        isPlayerOne = true;

        addGUIComp();
    }

    private void addGUIComp() {
        //Bar
        JLabel barLabel = new JLabel();
        barLabel.setOpaque(true);
        barLabel.setBackground(CommonConstants.BAR_COLOR);
        barLabel.setBounds(0, 0, CommonConstants.FRAME_SIZE.width, 25);

        //Turn Label
        turnLabel = new JLabel(CommonConstants.X_Label);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
        turnLabel.setPreferredSize(new Dimension(100, turnLabel.getPreferredSize().height));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(CommonConstants.X_COLOR);
        turnLabel.setForeground(CommonConstants.BOARD_COLOR);
        turnLabel.setBounds(
                (CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width)/2,
                0,
                turnLabel.getPreferredSize().width,
                turnLabel.getPreferredSize().height
        );

        //ScoreLabel
        scoreLabel = new JLabel(CommonConstants.SCORE_LABEL);
        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(CommonConstants.BOARD_COLOR);
        scoreLabel.setBounds(
                0,
                turnLabel.getY() + turnLabel.getPreferredSize().height + 25,
                CommonConstants.FRAME_SIZE.width,
                scoreLabel.getPreferredSize().height
        );

        //Grid Board
        GridLayout gridLayout = new GridLayout(3, 3);
        JPanel boardPanel = new JPanel(gridLayout);
        boardPanel.setBounds(
                0,
                scoreLabel.getY() + scoreLabel.getPreferredSize().height + 35,
                CommonConstants.BOARD_SIZE.width,
                CommonConstants.BOARD_SIZE.height
        );

        //Create board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                JButton jButton = new JButton();
                jButton.setFont(new Font("Dialog", Font.PLAIN, 180));
                jButton.setPreferredSize(CommonConstants.BUTTON_SIZE);
                jButton.setBackground(CommonConstants.BACKGROUND_COLOR);
                jButton.addActionListener(this);
                jButton.setBorder(BorderFactory.createLineBorder(CommonConstants.BOARD_COLOR));

                board[i][j] = jButton;
                boardPanel.add(board[i][j]);
            }
        }

        //Reset Button
        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Dialog", Font.PLAIN, 24));
        resetBtn.setBackground(CommonConstants.BOARD_COLOR);
        resetBtn.addActionListener(this);
        resetBtn.setBounds(
                (CommonConstants.FRAME_SIZE.width - resetBtn.getPreferredSize().width)/2,
                CommonConstants.FRAME_SIZE.height - 100,
                resetBtn.getPreferredSize().width,
                resetBtn.getPreferredSize().height
        );

        getContentPane().add(turnLabel);
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(resetBtn);
    }

    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(2, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                resetGame();
            }
        });

        //result label
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        resultLabel.setForeground(CommonConstants.BOARD_COLOR);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //reset Button
        JButton resetBtn = new JButton("Play Again");
        resetBtn.setBackground(CommonConstants.BOARD_COLOR);
        resetBtn.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(resetBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Play Again")){
            resetGame();

            //Only reset Score when reset pressed
            if(command.equals("Reset"))
                xScore = oScore = 0;

            if(command.equals("Play Again"))
                resultDialog.setVisible(false);

        }else{
            JButton btn = (JButton) e.getSource();
            if(btn.getText().equals("")){
                moveCounter++;

                //mark button with x/o if btn not selected yet
                if(isPlayerOne){
                    //player one (X-player)
                    btn.setText(CommonConstants.X_Label);
                    btn.setForeground(CommonConstants.X_COLOR);

                    //update turn label
                    turnLabel.setText(CommonConstants.O_Label);
                    turnLabel.setBackground(CommonConstants.O_COLOR);

                    //update turn
                    isPlayerOne = false;
                }else {
                    //Player two (player o)
                    btn.setText(CommonConstants.O_Label);
                    btn.setForeground(CommonConstants.O_COLOR);

                    //update turn label
                    turnLabel.setText(CommonConstants.X_Label);
                    turnLabel.setBackground(CommonConstants.X_COLOR);

                    //update turn
                    isPlayerOne = true;
                }

                //check win condition
                if(isPlayerOne){
                    // for X
                    checkOWin();
                }else{
                    checkXWin();
                }

                //draw condition
                checkDraw();

                //update score label
                scoreLabel.setText("X:" + xScore + " | O:" + oScore);
            }

            repaint();
            revalidate();
        }
    }

    private void checkXWin() {
        String result = "X Wins";

        //check rows
        for (int rows = 0; rows < board.length; rows++) {
            if(board[rows][0].getText().equals("X") && board[rows][1].getText().equals("X") && board[rows][2].getText().equals("X")){
                resultLabel.setText(result);

                //display result dailog
                resultDialog.setVisible(true);

                //update score
                xScore++;
            }
        }

        //check cols
        for (int cols = 0; cols < board.length; cols++) {
            if(board[0][cols].getText().equals("X") && board[1][cols].getText().equals("X") && board[2][cols].getText().equals("X")){
                resultLabel.setText(result);

                //display result dailog
                resultDialog.setVisible(true);

                //update score
                xScore++;
            }
        }

        //check diagonals
        if(board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")){
            resultLabel.setText(result);

            //display result dailog
            resultDialog.setVisible(true);

            //update score
            xScore++;
        }

        if(board[2][0].getText().equals("X") && board[1][1].getText().equals("X") && board[0][2].getText().equals("X")){
            resultLabel.setText(result);

            //display result dailog
            resultDialog.setVisible(true);

            //update score
            xScore++;
        }
    }

    private void checkOWin() {
        String result = "O Wins";

        //check rows
        for (int rows = 0; rows < board.length; rows++) {
            if(board[rows][0].getText().equals("O") && board[rows][1].getText().equals("O") && board[rows][2].getText().equals("O")){
                resultLabel.setText(result);

                //display result dailog
                resultDialog.setVisible(true);

                //update scoro
                oScore++;
            }
        }

        //check cols
        for (int cols = 0; cols < board.length; cols++) {
            if(board[0][cols].getText().equals("O") && board[1][cols].getText().equals("O") && board[2][cols].getText().equals("O")){
                resultLabel.setText(result);

                //display result dailog
                resultDialog.setVisible(true);

                //update score
                oScore++;
            }
        }

        //check diagonals
        if(board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")){
            resultLabel.setText(result);

            //display result dailog
            resultDialog.setVisible(true);

            //update score
            oScore++;
        }

        if(board[0][2].getText().equals("O") && board[1][1].getText().equals("O") && board[2][0].getText().equals("O")){
            resultLabel.setText(result);

            //display result dailog
            resultDialog.setVisible(true);

            //update score
            oScore++;
        }
    }

//    private void checkDraw() {
//        if(moveCounter >= 9){
//            resultLabel.setText("Draw!");
//            resultDialog.setVisible(true);
//        }
//    }

    private void checkDraw() {
        if (moveCounter == 9 && !resultDialog.isVisible()) {
            resultLabel.setText("Draw!");
            resultDialog.setVisible(true);
        }
    }


    private void resetGame() {
        //reset player back to X-player
        isPlayerOne = true;
        turnLabel.setText(CommonConstants.X_Label);
        turnLabel.setBackground(CommonConstants.X_COLOR);

        //reset ScoreLabel
        scoreLabel.setText(CommonConstants.SCORE_LABEL);

        //reset move counter
        moveCounter = 0;

        //reset board
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setText("");
            }
        }
    }
}
