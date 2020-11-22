let Modal = {
    open: false, status: function (status, text) {
        if (status == "new") {
            $(".modal .modal-title-add")[0].classList.remove("hide")
            $(".modal .modal-title-add")[0].innerText = text
            $(".modal .modal-title-edit")[0].classList.add("hide")
        } else if (status == "edit") {
            $(".modal .modal-title-add")[0].classList.add("hide")
            $(".modal .modal-title-edit")[0].classList.remove("hide")
            $(".modal .modal-title-edit")[0].innerText = text
        }
    }
}

function openModal() {
    if (Modal.open)
        return
    if (this.before)
        this.before()
    Modal.open = true
    let self = this;
    setTimeout(function () {
        $("body").classList.add("modal-open")
        $("#" + self.modal).classList.add("show")
        $("#" + self.modal).style.display = "block"
        $("#modal-backdrop").classList.add("show")
        $("#modal-backdrop").onclick = closeModal
        $("#modal-backdrop").ontouchstart = closeModal
    }, 300)
}

function closeModal() {
    setTimeout(function () {
        Modal.open = false
    }, 500)
    $("body").classList.remove("modal-open")
    $("#modal-backdrop").classList.remove("show")
    try {
        $("#" + this.modal).style.display = "none"
        $("#" + this.modal).classList.remove("show")
    } catch (e) {
        let modalId = this.getAttribute("modal");
        $("#" + modalId).style.display = "none"
        $("#" + modalId).classList.remove("show")
    }
    $("#modal-backdrop").removeEventListener("click", closeModal)
    $("#modal-backdrop").removeEventListener("touchstart", closeModal)
}