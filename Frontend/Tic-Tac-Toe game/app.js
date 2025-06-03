let boxes = document.querySelectorAll(".box"); 
let resetBtn = document.querySelector("#reset-btn")
let newBtn = document.querySelector("#new-btn");
let msgContainer = document.querySelector(".msg-container");
let msgPara = document.querySelector("#msg-para");            

let turnO = true;

const winPatterns = [
    [0, 1, 2],
    [0, 3, 6],
    [0, 4, 8],
    [1, 4, 7],
    [2, 5, 8],
    [2, 4, 6],
    [3, 4, 5],
    [6, 7, 8]
];

const resetGame= ()=>{
    turnO = true;
    enable();
    msgContainer.classList.add("hide");

}

const disable = ()=>{
    for(let box of boxes){
        box.disabled = true; 
    }
}

const enable = ()=>{
    for(let box of boxes){
        box.disabled = false;
        box.innerHTML=""; 
    }
}

boxes.forEach((box) => {
    box.addEventListener("click", ()=>{
        console.log("Button was Clicked");

        if(turnO){
            box.innerHTML = "O";
            turnO = false;
            
        }
        else{
            box.innerHTML = "X";
            turnO = true;
        }
        box.disabled = true;
        checkWinner();
    });
});

const checkWinner = ()=>{
    for(let pattern of winPatterns){
        let pos1Val = boxes[pattern[0]].innerHTML;
        let pos2Val = boxes[pattern[1]].innerHTML;
        let pos3Val = boxes[pattern[2]].innerHTML;

        if(pos1Val !="" && pos2Val !="" && pos3Val != ""){
            if(pos1Val==pos2Val && pos2Val==pos3Val){
                console.log("Winner", pos1Val);
                showWinner(pos1Val);
                disable();
            }
        }
    }
};

const showWinner = (winner)=>{
    msgPara.innerHTML = `Congratulations, Winner is Player ${winner}`;
    msgContainer.classList.remove("hide");
}

resetBtn.addEventListener("click", resetGame);
newBtn.addEventListener("click", resetGame);


