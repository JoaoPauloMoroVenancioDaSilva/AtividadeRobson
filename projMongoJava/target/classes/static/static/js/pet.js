// Pet.js

var API_URL = ""; // Se estiver no mesmo servidor Spring Boot, deixe vazio ou "/".

var listaPet = [];

/**
 * Formata os telefones.
 */
function formatarTelefone(fone) {
  // Remove tudo que não for dígito


  let digits = "";
  try{
    digits = fone.replace(/\D/g, "");
  } catch(erro){
    digits = "0000000000"
  }

  if (digits.length === 10) {
    // (XX) XXXX-XXXX
    const ddd = digits.slice(0, 2);
    const parte1 = digits.slice(2, 6);
    const parte2 = digits.slice(6);
    return `(${ddd}) ${parte1}-${parte2}`;
  } else if (digits.length === 11) {
    // (XX) XXXXX-XXXX
    const ddd = digits.slice(0, 2);
    const parte1 = digits.slice(2, 7);
    const parte2 = digits.slice(7);
    return `(${ddd}) ${parte1}-${parte2}`;
  } else {
    if (digits.length<10) {
      return fone + " [Fone inválido]";
    } else {
      return fone;
    }
  }
}

/*
* Formata a data
*/
function formatarData(dataISO) {
  // dataISO no formato "YYYY-MM-DD"
  let ano = "";
  let mes = "";
  let dia = "";
  try {
    [ano, mes, dia] = dataISO.split("-");    
  } catch (error) {
    try {
      [ano, mes, dia] = dataISO.split("/");
    } catch (error) {
      ano = "2000";
      mes = "01";
      dia = "01";
    }  
  }
  return `${dia}/${mes}/${ano}`;
}

function editarPet(pid) {
  // Busca a Pet na variável global
  const Pet = listaPet.find(p => p.id === pid);
  if (!Pet) {
    Swal.fire({
      icon: 'error',
      title: 'Erro na busca',
      text: "Pet com id:"+pid+" não foi localizada!"
    });
    return;
  }

  // Preenche os campos do formulário
  $("#inputId").val(Pet.id);
  $("#inputId").attr("disabled", false);
  $("#inputNome").val(Pet.nome);
  $("#inputFone").val(Pet.fone); // se quiser manter o formato original
  $("#inputAniversario").val(Pet.aniversario); // formato YYYY-MM-DD
  $('#staticBackdrop').modal('show');

  // Se quiser deixar o ID como somente leitura, pode fazer:
  // $("#inputId").prop("readonly", true);

  // Agora o usuário pode alterar os dados e depois clicar em "Atualizar".
}

/**
 * Carrega a lista de Pet e preenche a tabela.
 */
function carregarPet() {
  $.ajax({
    url: API_URL + "/pets",
    method: "GET",
    success: function(dados) {
      listaPet = dados;
      console.log(dados);
      const tbody = $("#tabelaPet tbody");
      tbody.empty();
      dados.forEach((p) => {
        tbody.append(`
          <tr>
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${formatarTelefone(p.fone)}</td>
            <td>${formatarData(p.aniversario)}</td>
            <td>
              <button class="btn btn-warning btn-sm" onclick="editarPet(${p.id})">Editar</button>
              <button class="btn btn-danger btn-sm" onclick="deletarPet(${p.id})">
                Deletar
              </button>
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
 * Cria uma nova Pet (POST).
 */
function criarPet() {
  const Pet = {
    id: parseInt($("#inputId").val()),
    nome: $("#inputNome").val(),
    fone: $("#inputFone").val(),
    aniversario: $("#inputAniversario").val()
  };
  $.ajax({
    url: API_URL + "/pets",
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(Pet),
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
  $("#inputId").attr("disabled", true);
  $("#inputNome").val("");
  $("#inputFone").val("");
  $("#inputAniversario").val("");
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
 * Atualiza uma Pet (PUT).
 */
function atualizarPet() {
  const pid = parseInt($("#inputId").val());
  const dados = {
    nome: $("#inputNome").val(),
    fone: $("#inputFone").val(),
    aniversario: $("#inputAniversario").val()
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
 * Deleta uma Pet (DELETE).
 */
function deletarPet(pid) {
  Swal.fire({
    title: 'Confirmação de Exclusão',
    text: `Tem certeza que deseja excluir a Pet de ID ${pid}?`,
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
          Swal.fire({ icon: 'error', title: 'Erro', text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação' });
        }
      });
    }
  });
}

$(document).ready(function(){
  carregarPet();
})