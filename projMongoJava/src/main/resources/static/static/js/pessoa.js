var API_URL = "";
var listaPessoas = [];

function mostrarErro(err) {
  console.error(err);
  Swal.fire({
    icon: 'error',
    title: 'Erro',
    text: (err.responseJSON && err.responseJSON.erro) || 'Falha na operação'
  });
}

function formatarTelefone(fone) {
  let digits = "";
  try {
    digits = (fone || "").replace(/\D/g, "");
  } catch (erro) {
    digits = "0000000000";
  }

  if (digits.length === 10) {
    const ddd = digits.slice(0, 2);
    const parte1 = digits.slice(2, 6);
    const parte2 = digits.slice(6);
    return `(${ddd}) ${parte1}-${parte2}`;
  } else if (digits.length === 11) {
    const ddd = digits.slice(0, 2);
    const parte1 = digits.slice(2, 7);
    const parte2 = digits.slice(7);
    return `(${ddd}) ${parte1}-${parte2}`;
  } else {
    return digits.length < 10 ? (fone || "") + " [Fone inválido]" : fone;
  }
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

function carregarPessoas() {
  $.ajax({
    url: API_URL + "/pessoas",
    method: "GET",
    success: function(dados) {
      listaPessoas = dados;
      const tbody = $("#tabelaPessoas tbody");
      tbody.empty();
      dados.forEach((p) => {
        tbody.append(`
          <tr>
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${formatarTelefone(p.fone)}</td>
            <td>${formatarData(p.aniversario)}</td>
            <td>
              <button class="btn btn-warning btn-sm" onclick="editarPessoa(${p.id})">Editar</button>
              <button class="btn btn-danger btn-sm" onclick="deletarPessoa(${p.id})">Deletar</button>
            </td>
          </tr>
        `);
      });
    },
    error: mostrarErro
  });
}

function editarPessoa(pid) {
  const pessoa = listaPessoas.find(p => p.id === pid);
  if (!pessoa) {
    Swal.fire({
      icon: 'error',
      title: 'Erro na busca',
      text: "Pessoa com ID " + pid + " não foi localizada!"
    });
    return;
  }

  $("#inputId").val(pessoa.id).attr("disabled", true);
  $("#inputNome").val(pessoa.nome);
  $("#inputFone").val(pessoa.fone);
  $("#inputAniversario").val(pessoa.aniversario);
  $('#modalPessoa').modal('show');
}

function IncluirPessoa() {
  $("#inputId").val(0).attr("disabled", true);
  $("#inputNome").val("");
  $("#inputFone").val("");
  $("#inputAniversario").val("");
  $('#modalPessoa').modal('show');
}

function gravarDados() {
  const id = parseInt($("#inputId").val()) || 0;
  const dados = {
    nome: $("#inputNome").val(),
    fone: $("#inputFone").val(),
    aniversario: $("#inputAniversario").val()
  };

  $.ajax({
    url: API_URL + "/pessoas" + (id === 0 ? "" : "/" + id),
    method: id === 0 ? "POST" : "PUT",
    contentType: "application/json",
    data: JSON.stringify(dados),
    success: function(res) {
      Swal.fire({
        icon: 'success',
        title: 'Sucesso',
        text: res.mensagem
      });
      $('#modalPessoa').modal('hide');
      carregarPessoas();
    },
    error: mostrarErro
  });
}

function deletarPessoa(pid) {
  Swal.fire({
    title: 'Confirmação de Exclusão',
    text: `Tem certeza que deseja excluir a pessoa de ID ${pid}?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sim, EXCLUIR!',
    cancelButtonText: 'Não'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: API_URL + "/pessoas/" + pid,
        method: "DELETE",
        success: function(res) {
          Swal.fire({
            icon: 'success',
            title: 'Sucesso',
            text: res.mensagem
          });
          carregarPessoas();
        },
        error: mostrarErro
      });
    }
  });
}