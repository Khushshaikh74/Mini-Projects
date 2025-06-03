//backend
package Lesson_1_RockPaperScissorGame;
import java.util.Random;

public class RockPaperScissor {
    private static final String compChoices[] = {"Rock", "Paper", "Scissor"};

    public String getCompChoice() {
        return compChoice;
    }

    public int getCompScore() {
        return compScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    private String compChoice;
    private int compScore, playerScore;

    private Random random;
    public RockPaperScissor(){
        random = new Random();
    }


    public String playRockPaperScissor(String playerChoice){
        compChoice = compChoices[random.nextInt(compChoices.length)];
        String result;

        if(compChoice.equals("Rock")){
            if(playerChoice.equals("Paper")){
                result = "Player Wins";
                playerScore++;
            } else if (playerChoice.equals("Scissor")) {
                result = "Computer Wins";
                compScore++;
            }else{
                result = "Draw";
            }
        }else if(compChoice.equals("Paper")){
            if(playerChoice.equals("Scissor")){
                result = "Player Wins";
                playerScore++;
            } else if (playerChoice.equals("Rock")) {
                result = "Computer Wins";
                compScore++;
            }else{
                result = "Draw";
            }
        }else{
            if(playerChoice.equals("Rock")){
                result = "Player Wins";
                playerScore++;
            } else if (playerChoice.equals("Paper")) {
                result = "Computer Wins";
                compScore++;
            }else{
                result = "Draw";
            }
        }
        return result;
    }

}
