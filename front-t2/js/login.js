var API_URL = "http://localhost:8081/api/";

function criarConfigAutenticacao(usuario, senha) {
    return {
        method: 'get',
        url: API_URL + 'usuario/login',
        crossDomain: true,
        headers: {
            'usuario': usuario,
            'senha': senha
        }
    };
}

async function logar(usuario, senha) {
    var config = criarConfigAutenticacao(usuario, senha)

    return await axios(config)
}
