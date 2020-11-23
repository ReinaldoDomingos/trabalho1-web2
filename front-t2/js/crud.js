var API_URL = "http://localhost:8081/api/", produtos = [];

function criarConfig(url, metodo, data) {
    let requisicao = {
        method: metodo ? metodo : 'get',
        url: url,
        crossDomain: true,
        headers: {
            'usuario': 'reinaldo',
            'senha': '123'
        }
    };

    if (data) {
        requisicao.data = data;
    }
    return requisicao;
}

async function getItens(tipo) {
    let url = API_URL + tipo;
    var config = criarConfig(url);

    return await axios(config)
}

async function getItem(tipo, id) {
    let url = API_URL + tipo + "/" + id;
    let config = criarConfig(url, 'get');

    return await axios(config);
}

async function postItem(tipo, item) {
    if (!tipo || !item) return;
    let url = API_URL + tipo;
    var config = criarConfig(url, 'post', item);

    return await axios(config)
}

async function deletar(tipo, id) {
    let url = API_URL + tipo + "/" + id;
    var config = criarConfig(url, 'delete');

    return axios(config);
}

async function updateItem(tipo, item) {
    let url = API_URL + tipo + "/" + item.id;
    var config = criarConfig(url, 'put', item);

    return await axios(config);
}

let qtd_mocks_para_processar = 20;

async function setProdutos() {
    let produtos = []
    for (let i = 0; i < qtd_mocks_para_processar; i++) {
        let produto = {
            "nome": "Cavaleiros Do Zodíaco - Box Blu-ray Alma De Ouro",
            "urlImg": "https://picsum.photos/200/300?grayscale",
            "descricao": "teste"
        }
        produto.quantidade = ((Math.random() * 50 + 20) * 1.075).toPrecision(2)
        produto.valorDeVenda = ((Math.random() * 100 + 25) * 1.075).toPrecision(2)
        produto.custo = ((Math.random() * 70 + 20) * 1.075).toPrecision(2)
        produtos.push(produto)
    }

    const promises = produtos.map(async (produto, id) => {
        await postItem("produto", produto)
    });


    await Promise.all(promises);
    $("#msg-list-empty").addClass("hide")
    return {};
}