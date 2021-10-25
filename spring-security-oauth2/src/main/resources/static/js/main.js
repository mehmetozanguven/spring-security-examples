const inputs = document.querySelectorAll(".form__input")

function addfocus(){
    var parent = this.parentNode.parentNode
    parent.classList.add("focus")
}

function remfocus(){
    var parent = this.parentNode.parentNode
    if(this.value == ""){
        parent.classList.remove("focus")
    }
}

inputs.forEach(input=>{
    input.addEventListener("focus",addfocus)
    input.addEventListener("blur",remfocus)
})