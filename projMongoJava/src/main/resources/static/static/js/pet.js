var API_URL = "";
var listaPet = [];

function mostrarErro(err) {
  console.error(err);
  Swal.fire({
    icon: 'error',
    title: 'Erro',
    text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação'
  });
}

function formatarData(dataISO) {
  if (!dataISO) return "";
  let ano = "", mes = "", dia = "";
  try {
    if (dataISO.includes("-")) {
      [ano, mes, dia] = dataISO.split("-");
    } else if (dataISO.includes("/")) {
      [ano, mes, dia] = dataISO.split("/");
    }
  } catch (error) {
    return dataISO;
  }
  return `${dia}/${mes}/${ano}`;
}

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
            <td>${p.nome}</td>
            <td>${p.raca}</td>
            <td>${formatarData(p.idade)}</td>
            <td>
              <button class="btn btn-warning btn-sm" onclick="editarPet(${p.id})">Editar</button>
              <button class="btn btn-danger btn-sm" onclick="deletarPet(${p.id})">Deletar</button>
            </td>
          </tr>
        `);
      });
    },
    error: mostrarErro
  });
}

function editarPet(pid) {
  const pet = listaPet.find(p => p.id === pid);
  if (!pet) {
    Swal.fire({
      icon: 'error',
      title: 'Erro na busca',
      text: "Pet com ID " + pid + " não foi localizado!"
    });
    return;
  }

  $("#inputId").val(pet.id).attr("disabled", true);
  $("#inputNome").val(pet.nome);
  $("#inputRaca").val(pet.raca);
  $("#inputIdade").val(pet.idade);
  $('#modalPet').modal('show');
}

function IncluirPet() {
  $("#inputId").val(0).attr("disabled", true);
  $("#inputNome").val("");
  $("#inputRaca").val("");
  $("#inputIdade").val("");
  $('#modalPet').modal('show');
}

function gravarDados() {
  const pid = parseInt($("#inputId").val()) || 0;
  const dados = {
    nome: $("#inputNome").val(),
    raca: $("#inputRaca").val(),
    idade: $("#inputIdade").val()
  };

  $.ajax({
    url: API_URL + "/pets" + (pid === 0 ? "" : "/" + pid),
    method: pid === 0 ? "POST" : "PUT",
    contentType: "application/json",
    data: JSON.stringify(dados),
    success: function(res) {
      Swal.fire({
        icon: 'success',
        title: 'Sucesso',
        text: res.mensagem
      });
      $('#modalPet').modal('hide');
      carregarPet();
    },
    error: mostrarErro
  });
}

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
        error: mostrarErro
      });
    }
  });
}