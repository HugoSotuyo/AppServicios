// Habilitar el botón "Registrarme" si se seleccionan los términos y condiciones
document.getElementById("terminos").addEventListener("change", function () {
    const registrarmeBtn = document.getElementById("registrarme");
    registrarmeBtn.disabled = !this.checked;
});
