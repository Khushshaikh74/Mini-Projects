let userScore = 0;
let CompScore = 0;

let choices = document.querySelectorAll(".choice");
let resultMsg = document.querySelector("#msg");
let msgCotainer = document.querySelector(".msg-container");
let userWinScore = document.querySelector("#user-score");
let compWinScore = document.querySelector("#comp-score");

choices.forEach(choice =>{
    choice.addEventListener("click", ()=>{
        let userChoice = choice.getAttribute("id");
        // console.log("Choice was Clicked" , userChoice);
        playGame(userChoice);
    });
});

const draw = ()=>{
    // console.log("Match was Draw");
    resultMsg.innerHTML = "Match was Draw, play again";
    msgCotainer.style.backgroundColor = "black";
    
}

const compSelect = ()=>{
    let option = ["rock", "paper", "scissor"]
    let compPick = Math.floor(Math.random() * 3);
    let selectedOption = option[compPick];
    return selectedOption;
}

const playGame = (userChoice)=>{
    // console.log("UserChoice = ", userChoice);
    let compChoice = compSelect();
    // console.log("CompChoice = ", compChoice);

    if(userChoice === compChoice){
        draw();
    }
    else{
        let userWin = true;
        if(userChoice === "rock"){
            //comp = paper, scissor
            userWin = compChoice === "scissor"? true : false; 
        }
        else if(userChoice === "paper"){
            //comp = rock, scissor
            userWin = compChoice === "rock"? true : false;
        }
        else{
            //comp = rock, paper
            userWin = compChoice === "paper"? true : false;
        }

        showWinner(userWin, userChoice, compChoice);

    }
}

const showWinner = (userWin, userChoice, compChoice)=>{
    if(userWin === true){
        userScore++;
        userWinScore.innerHTML = `${userScore}`;
        // console.log("user Win");
        resultMsg.innerText = `you win, your ${userChoice} beats ${compChoice}`;
        msgCotainer.style.backgroundColor = "green";
    }
    else{
        CompScore++;
        compWinScore.innerHTML = `${CompScore}`;
        // console.log("Comp Win");
        resultMsg.innerText = `you lose, ${compChoice} beats your ${userChoice}`;
        msgCotainer.style.backgroundColor = "red";
    }
}