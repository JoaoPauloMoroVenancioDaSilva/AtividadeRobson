// agendamento.js
var API_URL = "";
var listaAgendamento = [];

function mostrarErro(err) {
  console.error(err);
  Swal.fire({ icon: 'error', title: 'Erro', text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação' });
}

function formatarDataHora(valor) {
  // "YYYY-MM-DDTHH:MM" -> "DD/MM/YYYY HH:MM"
  if (!valor) return "";
  const [data, hora] = valor.split("T");
  const [ano, mes, dia] = data.split("-");
  return `${dia}/${mes}/${ano}` + (hora ? " " + hora : "");
}

/** Preenche um <select> com os registros de uma rota (/pessoas, /pets, /locais). */
function carregarCombo(rota, seletor) {
  return $.get(API_URL + rota, function(dados) {
    const sel = $(seletor);
    sel.empty().append(`<option value="">Selecione...</option>`);
    dados.forEach(d => sel.append(`<option value="${d.id}">${d.id} - ${d.nome}</option>`));
  });
}

function carregarCombos() {
  return $.when(
    carregarCombo("/pessoas", "#selPessoa"),
    carregarCombo("/pets", "#selPet"),
    carregarCombo("/locais", "#selLocal")
  );
}

function carregarAgendamento() {
  $.get(API_URL + "/agendamentos", function(dados) {
    listaAgendamento = dados;
    const tbody = $("#tabelaAgendamento tbody");
    tbody.empty();
    dados.forEach((a) => {
      tbody.append(`
        <tr>
          <td>${a.id}</td>
          <td>${formatarDataHora(a.data)}</td>
          <td>${a.pessoaNome}</td>
          <td>${a.petNome}</td>
          <td>${a.localNome}</td>
          <td>${a.descricao || ""}</td>
          <td>
            <button class="btn btn-warning btn-sm" onclick="editarAgendamento(${a.id})">Editar</button>
            <button class="btn btn-danger btn-sm" onclick="deletarAgendamento(${a.id})">Deletar</button>
          </td>
        </tr>`);
    });
  }).fail(mostrarErro);
}

function preencher(a) {
  $("#inputId").val(a.id).attr("disabled", true);
  $("#inputData").val(a.data);
  $("#selPessoa").val(a.pessoaId);
  $("#selPet").val(a.petId);
  $("#selLocal").val(a.localId);
  $("#inputDescricao").val(a.descricao);
}

function IncluirAgendamento() {
  carregarCombos().then(function() {
    preencher({ id: 0, data: "", pessoaId: "", petId: "", localId: "", descricao: "" });
    $('#staticBackdrop').modal('show');
  });
}

function editarAgendamento(pid) {
  const a = listaAgendamento.find(x => x.id === pid);
  if (!a) return;
  carregarCombos().then(function() {
    preencher(a);
    $('#staticBackdrop').modal('show');
  });
}

function gravarDados() {
  const pid = parseInt($("#inputId").val());
  const dados = {
    pessoaId: parseInt($("#selPessoa").val()) || null,
    petId: parseInt($("#selPet").val()) || null,
    localId: parseInt($("#selLocal").val()) || null,
    data: $("#inputData").val(),
    descricao: $("#inputDescricao").val()
  };
  $.ajax({
    url: API_URL + "/agendamentos" + (pid == 0 ? "" : "/" + pid),
    method: pid == 0 ? "POST" : "PUT",
    contentType: "application/json",
    data: JSON.stringify(dados),
    success: function(res) {
      Swal.fire({ icon: 'success', title: 'Sucesso', text: res.mensagem });
      carregarAgendamento();
    },
    error: mostrarErro
  });
}

function deletarAgendamento(pid) {
  Swal.fire({
    title: 'Confirmação de Exclusão',
    text: `Tem certeza que deseja excluir o Agendamento de ID ${pid}?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sim, EXCLUIR!',
    cancelButtonText: 'Não'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: API_URL + "/agendamentos/" + pid,
        method: "DELETE",
        success: function(res) {
          Swal.fire({ icon: 'success', title: 'Sucesso', text: res.mensagem });
          carregarAgendamento();
        },
        error: mostrarErro
      });
    }
  });
}

$(document).ready(function() {
  carregarAgendamento();
});
