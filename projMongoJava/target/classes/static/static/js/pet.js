var API_URL = ""; 

var listaPet = [];

/*
- Formata a data (DD/MM/YYYY)
*/
function formatarData(dataISO) {
  if (!dataISO) return "";
  let ano = "", mes = "", dia = "";
  try {
    [ano, mes, dia] = dataISO.split("-");    
  } catch (error) {
    try {
      [ano, mes, dia] = dataISO.split("/");
    } catch (error) {
      return dataISO;
    }  
  }
  return `${dia}/${mes}/${ano}`;
}

function editarPet(pid) {
  const pet = listaPet.find(p => p.id === pid);
  if (!pet) {
    Swal.fire({
      icon: 'error',
      title: 'Erro na busca',
      text: "Pet com id: " + pid + " não foi localizado!"
    });
    return;
  }

  // Preenche os campos do formulário
  $("#inputId").val(pet.id);
  $("#inputId").prop("disabled", false);
  $("#inputNome").val(pet.nome);
  $("#inputRaca").val(pet.raca);
  $("#inputIdade").val(pet.idade); 
  $('#staticBackdrop').modal('show');
}

/**
- Carrega a lista de Pets e preenche a tabela.
*/
function carregarPet() {
  $.ajax({
    url: API_URL + "/pets",
    method: "GET",
    success: function(dados) {
      listaPet = dados;
      const tbody = $("#tabelaPet tbody");
      tbody.empty();
      dados.forEach((p) => {
        tbody.append(`
          <tr>
            <td>${p.id}</td>
            <td>${p.nome || ''}</td>
            <td>${p.raca || ''}</td>
            <td>${formatarData(p.idade)}</td>
            <td>
              <button class="btn btn-warning btn-sm" onclick="editarPet(${p.id})">Editar</button>
              <button class="btn btn-danger btn-sm" onclick="deletarPet(${p.id})">Deletar</button>
            </td>
          </tr>
        `);
      });
    },
    error: function(err) {
      console.error(err);
    }
  });
}

/**
- Cria um novo Pet (POST).
*/
function criarPet() {
  const pet = {
    nome: $("#inputNome").val(),
    raca: $("#inputRaca").val(),
    idade: $("#inputIdade").val()
  };
  $.ajax({
    url: API_URL + "/pets",
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(pet),
    success: function(res) {
      Swal.fire({
        icon: 'success',
        title: 'Sucesso',
        text: res.mensagem
      });
      carregarPet();
    },
    error: function(err) {
      console.error(err);
    }
  });
}

function IncluirPet() {
  $("#inputId").val(0);
  $("#inputId").prop("disabled", true);
  $("#inputNome").val("");
  $("#inputRaca").val("");
  $("#inputIdade").val("");
  $('#staticBackdrop').modal('show');
}

function gravarDados() {
  let id = $("#inputId").val();
  if (id == 0) {
    criarPet();
  } else {
    atualizarPet();
  }
}

/**
- Atualiza um Pet (PUT).
*/
function atualizarPet() {
  const pid = parseInt($("#inputId").val());
  const dados = {
    nome: $("#inputNome").val(),
    raca: $("#inputRaca").val(),
    idade: $("#inputIdade").val()
  };
  $.ajax({
    url: API_URL + "/pets/" + pid,
    method: "PUT",
    contentType: "application/json",
    data: JSON.stringify(dados),
    success: function(res) {
      Swal.fire({
        icon: 'success',
        title: 'Sucesso',
        text: res.mensagem
      });
      carregarPet();
    },
    error: function(err) {
      console.error(err);
    }
  });
}

/**
- Deleta um Pet (DELETE).
*/
function deletarPet(pid) {
  Swal.fire({
    title: 'Confirmação de Exclusão',
    text: `Tem certeza que deseja excluir o Pet de ID ${pid}?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sim, EXCLUIR!',
    cancelButtonText: 'Não'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: API_URL + "/pets/" + pid,
        method: "DELETE",
        success: function(res) {
          Swal.fire({
            icon: 'success',
            title: 'Sucesso',
            text: res.mensagem
          });
          carregarPet();
        },
        error: function(err) {
          console.error(err);
          Swal.fire({ 
            icon: 'error', 
            title: 'Erro', 
            text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação' 
          });
        }
      });
    }
  });
}

$(document).ready(function(){
  carregarPet();
});
