//frontend
package Lesson_1_RockPaperScissorGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorGUI extends JFrame implements ActionListener{

    JButton rockBtn, scissorBtn, paperBtn;
    JLabel compChoiceLabel, compScoreLabel, playerScoreLabel;
    //backend object
    RockPaperScissor rockPaperScissor;

    public RockPaperScissorGUI(){
        super("Rock Paper Scissor Game");
        setSize(450, 574);
        setLayout(null);

        //Terminate JVM when closing GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        rockPaperScissor = new RockPaperScissor();

        addGUIComponents();
    }

    private void addGUIComponents(){

        //Computer Score Label
        compScoreLabel = new JLabel("Computer: 0");
        compScoreLabel.setBounds(0,43,450,30);
        compScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        compScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(compScoreLabel);

        //Computer Choice Label
        compChoiceLabel = new JLabel("?");
        compChoiceLabel.setBounds(175, 118, 98, 81);
        compChoiceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        compChoiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        compChoiceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(compChoiceLabel);

        //Player Score Label
        playerScoreLabel = new JLabel("Player: 0");
        playerScoreLabel.setBounds(0, 317, 450, 30);
        playerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerScoreLabel);

        //Player Choice Button
        rockBtn = new JButton("Rock");
        rockBtn.setBounds(40, 387, 105, 81);
        rockBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        rockBtn.addActionListener(this);
        add(rockBtn);

        paperBtn = new JButton("Paper");
        paperBtn.setBounds(165, 387, 105, 81);
        paperBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        paperBtn.addActionListener(this);
        add(paperBtn);

        scissorBtn = new JButton("Scissor");
        scissorBtn.setBounds(290, 387, 105, 81);
        scissorBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        scissorBtn.addActionListener(this);
        add(scissorBtn);

        //displayResult("Test Message");
    }

    //Display msg for result and try again btn
    private void displayResult(String message){
        //Owner refers to parent obj in this case is our GUI which is represented bt 'this' keyword
        //modal refers to property of a dialog box which needs user's immediate attention and block input to anything until it's closed
        JDialog resultDialog = new JDialog(this, "Result", true);
        resultDialog.setSize(227,124);
        resultDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        resultDialog.setResizable(false);

        //Msg Display Label
        JLabel msgResultLabel = new JLabel(message);
        msgResultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        msgResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultDialog.add(msgResultLabel, BorderLayout.CENTER);

        //try again btn
        JButton tryAgainBtn = new JButton("Try Again ?");
        tryAgainBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compChoiceLabel.setText("?");
                resultDialog.dispose();
            }
        });
        resultDialog.add(tryAgainBtn, BorderLayout.SOUTH);

        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }

    //It is called when GUI component that has their action listener set to our class is activated
    @Override
    public void actionPerformed(ActionEvent e) {
        String playerChoice = e.getActionCommand().toString();
        String result = rockPaperScissor.playRockPaperScissor(playerChoice);
        compChoiceLabel.setText(rockPaperScissor.getCompChoice());

        compScoreLabel.setText("Computer: " + rockPaperScissor.getCompScore());
        playerScoreLabel.setText("Player: " + rockPaperScissor.getPlayerScore());
        displayResult(result);
    }
}

