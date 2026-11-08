$(document).ready(function() {
    $("#linkPessoas").click(function(e) {
        e.preventDefault();
        $("#divPrincipal").load("/view/pessoa", function() {
            $.getScript("/static/js/pessoa.js").done(function() {
                carregarPessoas();
            });
        });
    });

    $("#linkPet").click(function(e) {
        e.preventDefault();
        $("#divPrincipal").load("/view/pet", function() {
            $.getScript("/static/js/pet.js").done(function() {
                carregarPet();
            });
        });
    });

    $("#linkLocal").click(function(e) {
        e.preventDefault();
        $("#divPrincipal").load("/view/local", function() {
            $.getScript("/static/js/local.js").done(function() {
                carregarLocal();
            });
        });
    });

    $("#linkAgendamento").click(function(e) {
        e.preventDefault();
        $("#divPrincipal").load("/view/agendamento", function() {
            $.getScript("/static/js/agendamento.js").done(function() {
                carregarAgendamento();
            });
        });
    });
});