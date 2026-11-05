// local.js
var API_URL = "";
var listaLocal = [];

function mostrarErro(err) {
  console.error(err);
  Swal.fire({ icon: 'error', title: 'Erro', text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação' });
}

function carregarLocal() {
  $.get(API_URL + "/locais", function(dados) {
    listaLocal = dados;
    const tbody = $("#tabelaLocal tbody");
    tbody.empty();
    dados.forEach((l) => {
      tbody.append(`
        <tr>
          <td>${l.id}</td>
          <td>${l.nome}</td>
          <td>${l.endereco || ""}</td>
          <td>${l.cidade || ""}</td>
          <td>
            <button class="btn btn-warning btn-sm" onclick="editarLocal(${l.id})">Editar</button>
            <button class="btn btn-danger btn-sm" onclick="deletarLocal(${l.id})">Deletar</button>
          </td>
        </tr>`);
    });
  }).fail(mostrarErro);
}

function preencher(l) {
  $("#inputId").val(l.id);
  $("#inputNome").val(l.nome);
  $("#inputEndereco").val(l.endereco);
  $("#inputCidade").val(l.cidade);
}

function IncluirLocal() {
  preencher({ id: 0, nome: "", endereco: "", cidade: "" });
  $("#inputId").attr("disabled", true);
  $('#staticBackdrop').modal('show');
}

function editarLocal(pid) {
  const l = listaLocal.find(x => x.id === pid);
  if (!l) return;
  preencher(l);
  $("#inputId").attr("disabled", true);
  $('#staticBackdrop').modal('show');
}

function gravarDados() {
  const pid = parseInt($("#inputId").val());
  const dados = {
    nome: $("#inputNome").val(),
    endereco: $("#inputEndereco").val(),
    cidade: $("#inputCidade").val()
  };
  $.ajax({
    url: API_URL + "/locais" + (pid == 0 ? "" : "/" + pid),
    method: pid == 0 ? "POST" : "PUT",
    contentType: "application/json",
    data: JSON.stringify(dados),
    success: function(res) {
      Swal.fire({ icon: 'success', title: 'Sucesso', text: res.mensagem });
      carregarLocal();
    },
    error: mostrarErro
  });
}

function deletarLocal(pid) {
  Swal.fire({
    title: 'Confirmação de Exclusão',
    text: `Tem certeza que deseja excluir o Local de ID ${pid}?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sim, EXCLUIR!',
    cancelButtonText: 'Não'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: API_URL + "/locais/" + pid,
        method: "DELETE",
        success: function(res) {
          Swal.fire({ icon: 'success', title: 'Sucesso', text: res.mensagem });
          carregarLocal();
        },
        error: mostrarErro
      });
    }
  });
}

$(document).ready(function() {
  carregarLocal();
});
