let inseriu_mock = false;
var API_URL = "http://localhost:8081/", categorias = [], produtos = [];
let categorias_mock = ["CDs, DVDs e Blu-ray"]

async function getItens(tipo) {
    console.log(tipo)
    return await axios.get(API_URL + "albus/" + tipo)
}

async function getItem(tipo, id,) {
    console.log(tipo)
    return await axios.get(API_URL + "albus/" + tipo + "/" + id)
}

async function postItem(tipo, item) {
    console.log(tipo)
    if (!tipo || !item) return;
    return await axios.post(API_URL + "albus/" + tipo, item)
}

async function deletar(tipo, id) {
    console.log(tipo)
    return axios.delete(API_URL + "albus/" + tipo + "/" + id);
}

async function updateItem(tipo, item) {
    console.log(tipo)
    return axios.put(API_URL + "albus/" + tipo + "/" + item.id, item);
}

let qtd_mocks_para_processar = 20;

async function setProdutos() {
    let produtos = []
    for (let i = 0; i < qtd_mocks_para_processar; i++) {
        let produto = {
            "nome": "Cavaleiros Do Zodíaco - Box Blu-ray Alma De Ouro",
            "urlImg": "https://picsum.photos/200/300?grayscale",
            "categoria": {"id": 1},
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

async function setCategorias() {
    return await postItem("categoria", {
        "nome": categorias_mock[0],
        "tipoCategoria": "PRODUTO"
    })
}
