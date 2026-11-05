// main.js
$(document).ready(function() {
    // Ao clicar em "Pessoas" no menu, carregamos pessoa.html
    $("#linkPessoas").click(function(e) {
      e.preventDefault();
      $("#divPrincipal").load("/view/pessoa", function() {
        // Após carregar o HTML, carregamos o script do CRUD
        $.getScript("static/js/pessoa.js")
          .done(function() {
            // Inicializa os eventos e carrega a lista de pessoas            
          })
          .fail(function() {
            console.error("Não foi possível carregar pessoa.js");
          });
      });
    });

    $("#linkPet").click(function(e) {
      e.preventDefault();
      $("#divPrincipal").load("/view/pet", function() {
        // Após carregar o HTML, carregamos o script do CRUD
        $.getScript("static/js/pet.js")
          .done(function() {
            // Inicializa os eventos e carrega a lista de pessoas            
          })
          .fail(function() {
            console.error("Não foi possível carregar pet.js");
          });
      });
    });

    $("#linkLocal").click(function(e) {
      e.preventDefault();
      $("#divPrincipal").load("/view/local", function() {
        $.getScript("static/js/local.js")
          .fail(function() {
            console.error("Não foi possível carregar local.js");
          });
      });
    });

    $("#linkAgendamento").click(function(e) {
      e.preventDefault();
      $("#divPrincipal").load("/view/agendamento", function() {
        $.getScript("static/js/agendamento.js")
          .fail(function() {
            console.error("Não foi possível carregar agendamento.js");
          });
      });
    });

  });
