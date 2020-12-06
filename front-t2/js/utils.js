const meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
    'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

const enums = {
    ATIVO: 'Ativo',
    INATIVO: 'Inativo',
    FISICA: 'Fisica',
    JURIDICA: 'Juridica'
};

function estaPreeenchidoCamposObrigatorios(item, camposObrigatorios) {

    for (let i = 0; i < camposObrigatorios.length; i++) {
        let atributo = camposObrigatorios[i];
        if (!item[atributo]) {
            return false;
        }
    }

    return true;
}

function formatarData(dataNascimento) {
    for (let i = 0; i < dataNascimento.length; i++) {
        dataNascimento[i] = dataNascimento[i] > 9 ? dataNascimento[i] : '0' + dataNascimento[i];
    }

    return dataNascimento.toString().replace(/,/gi, '-');
}
