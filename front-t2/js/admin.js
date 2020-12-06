function estaPreeenchidoCamposObrigatorios(item) {
    let camposObrigatorios = ['descricao', 'quantidadeEstoque', 'precoVendaFisica',
        'precoCompra', 'precoVendaJuridica'];

    for (let i = 0; i < camposObrigatorios.length; i++) {
        let atributo = camposObrigatorios[i];
        if (!item[atributo]) {
            return false;
        }
    }

    return true;
}

new Vue({
    el: "#app",
    data: {
        filtro1: '',
        filtro2: '',
        pessoas: [],
        produtos: [],
        editando: '',
        modalTitle: "",
        tab: "produtos",
        isLogado: false,
        msgErroLogin: '',
        itemEditando: {
            id: "",
            descricao: "",
            precoCompra: "",
            precoVendaFisica: "",
            precoVendaJuridica: "",
            quantidadeEstoque: "",
        },
        title: "Easy buy",
        modalAberto: false,
        filtroSelecionado: '',
        itemMenuSelecionado: 'inicio',
        filtrosProdutos: [
            {nome: 'Descrição', valor: 'descricao'},
            {nome: 'Preço', valor: 'preco'}
        ],
        filtrosPessoas: [
            {nome: 'Situação', valor: 'situacao'},
            {nome: 'Responsável', valor: 'responsavel'}
        ],
        width: (innerWidth > 540) ? 'desktop' : 'mobile',
        user: {nome: "Fulano de Tal", email: "email@company.com"},
        msgFooter: "Easy buy 2020 &copy; Todos os direitos reservados"
    },
    mixins: [Vue2Filters.mixin],
    created() {
        this.autenticarAutomatico();
        $(document).scroll(this.changeNavColor)
    },
    mounted() {
    },
    methods: {
        mostrarTab(tab) {
            if (this.tab === tab) {
                return;
            }

            console.log('tab', tab)
            this.tab = tab;
            this.pessoas = [];
            this.produtos = [];
            this.filtroSelecionado = '';

            if (this.tab === 'produtos') {
                this.carregarProdutos();
            } else if (this.tab === 'pessoas') {
                this.carregarPessoas();
            }
        },
        limparSelect() {
            this.filtro1 = '';
            this.filtro2 = '';
        },
        limparFiltro() {
            this.limparSelect();
            if (this.tab === 'produtos') {
                this.carregarProdutos();
            }

            if (this.tab === 'pessoas') {
                this.carregarPessoas();
            }
        },
        filtrar() {
            var filtros = [];

            if (this.tab === 'produtos') {
                if (this.filtroSelecionado === 'preco' && (this.filtro1 || this.filtro2)) {
                    if (this.filtro1) {
                        filtros.push({nome: 'precoMinimo', valor: this.filtro1});
                    }
                    if (this.filtro2) {
                        filtros.push({nome: 'precoMaximo', valor: this.filtro2});
                    }
                } else if (this.filtroSelecionado === 'descricao' && this.filtro1) {
                    filtros.push({nome: 'descricao', valor: this.filtro1});
                }
                this.carregarProdutos(filtros)
            }

            if (this.tab === 'pessoas') {
                if (this.filtroSelecionado === 'situacao' && this.filtro1) {
                    filtros.push({nome: 'situacao', valor: this.filtro1});
                } else if (this.filtroSelecionado === 'responsavel' && this.filtro2) {
                    filtros.push({nome: 'nomeResponsavel', valor: this.filtro2});
                }
                this.carregarPessoas(filtros);
            }
        },
        autenticarAutomatico() {
            let usuario = sessionStorage.usuario;
            let senha = sessionStorage.senha;

            if (usuario && senha) {
                this.login(usuario, senha);
            }
        },
        login(login, senha) {
            let self = this;

            if (!login || !senha) {
                login = $('#login').val();
                senha = $('#password').val();
            }

            logar(login, senha).then((response) => {
                let usuario = response.data;
                if (usuario.isAdministrador) {
                    self.isLogado = true;
                    sessionStorage.usuario = usuario.login;
                    sessionStorage.senha = usuario.senha;
                    self.msgErroLogin = '';
                    self.carregarDados()
                } else {
                    self.msgErroLogin = 'Usuário sem permissão!';
                }
            }).catch(() => {
                self.isLogado = false;
                self.msgErroLogin = 'Login e/ou senha incorretos!';
            })
        },
        changeNavColor() {
            var st1 = document.documentElement.scrollTop;
            var st2 = document.body.scrollTop;
            if (st1 > 0 || st2 > 0) {
                $('header').removeClass("top")
                $('nav').removeClass("top")
            } else {
                $('header').addClass("top")
                $('nav').addClass("top")
            }
        }
        ,
        selectNavItem(name) {
            this.itemMenuSelecionado = name
            if (innerWidth <= 540)
                this.navtoggle()
        },
        carregarProdutos(filtros) {
            getItens("produto", filtros).then(response => {
                if (response.status === 200 && (response.data.length > 0 || filtros)) {
                    this.produtos = response.data;
                } else if (!filtros) {
                    setProdutos().then(() => {
                        this.carregarProdutos()
                    })
                }
            })
        },
        carregarPessoas(filtros) {
            getItens("pessoa", filtros).then(response => {
                if (response.status === 200 && (response.data.length > 0 || filtros)) {
                    this.pessoas = response.data;
                } else if (!filtros) {
                    // setPessoas().then(() => {
                    //     this.carregarPessoas()
                    // })
                }
            })
        },
        carregarDados() {
            this.carregarProdutos()
        },
        adicionarItem() {
            this.toggleModal()
            if (this.tab === "produtos") {
                this.editando = ""
                this.modalTitle = "Novo Produto"
                this.itemEditando = {
                    id: "",
                    nome: "",
                    custo: "",
                    valorDeVenda: "",
                    quantidade: "",
                    descricao: "",
                    urlImg: ""
                }
            } else {
                this.itemEditando = {
                    id: "",
                    nome: "",
                    custo: "",
                    valorDeVenda: "",
                    quantidade: "",
                    descricao: "",
                    urlImg: ""
                }
            }
        },
        editarItem(item) {
            this.toggleModal()
            if (this.tab === "produtos") {
                this.modalTitle = "Editar Produto"

                getItem('produto', item.id).then(response => {
                    if (response.status === 200) {
                        this.itemEditando = response.data;
                    }
                })
            } else {
                this.itemEditando = {
                    id: item.id,
                    nome: item.nome,
                    custo: "",
                    valorDeVenda: "",
                    quantidade: "",
                    descricao: item.descricao,
                    urlImg: ""
                }
            }
        },
        salvarItem() {
            if (this.tab === "produtos") {
                if (!estaPreeenchidoCamposObrigatorios(this.itemEditando) || this.itemEditando === undefined || this.itemEditando === false) {
                    alert("Preencha todos os campos obrigatórios (*)!")
                    return
                }
                let item = this.itemEditando;
                if (!item.id)
                    postItem("produto", item)
                        .then(this.carregarProdutos)
                        .then(this.toggleModal)
                        .then(this.itemEditando = {
                            id: "",
                            nome: "",
                            custo: "",
                            valorDeVenda: "",
                            quantidade: "",
                            descricao: "",
                            urlImg: ""
                        })
                else
                    updateItem("produto", item)
                        .then(this.carregarProdutos)
                        .then(this.toggleModal)
                        .then(this.itemEditando = {
                            id: "",
                            nome: "",
                            custo: "",
                            valorDeVenda: "",
                            quantidade: "",
                            descricao: "",
                            urlImg: ""
                        })
            }
        },
        deletarItem(id) {
            if (!id) return;
            if (this.tab === "produtos") {
                deletar("produto", id)
                    .then(this.carregarProdutos)
            }
        },
        toggleModal() {
            if ($(".modal.show").length) {
                $(".modal").modal("hide")
            } else {
                $(".modal").modal()
            }
            this.modalAberto = !this.modalAberto
        }
    },
    filters: {
        formatarReal(value) {
            return 'R$' + ((value.toString().indexOf(',') === -1) ? value + ',00' : value)
        },
        formatarData(dataNascimento) {
            return dataNascimento[2] + ' de ' + meses[dataNascimento[1] - 1] + ' de ' + dataNascimento[0];
        },
        formatarEnum(chave) {
            return enums[chave];
        }
    }
});
