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
            if (this.tab === 'produtos') {
                this.carregarProdutos();
            } else if (this.tab === 'pessoas') {
                this.carregarPessoas();
            }
        },
        adicionarItem() {
            this.toggleModal()
            if (this.tab === "produtos") {
                this.editando = ""
                this.modalTitle = "Novo Produto"
                this.itemEditando = {id: ''}
            } else {
                this.itemEditando = {id: ''}
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
                });
            } else {
                this.modalTitle = "Editar Pessoa"

                getItem('pessoa', item.id).then(response => {
                    if (response.status === 200) {
                        this.itemEditando = response.data;
                        this.itemEditando.dataNascimento = formatarData(this.itemEditando.dataNascimento);
                    }
                });
            }
        },
        salvarItem() {
            let item = JSON.parse(JSON.stringify(this.itemEditando));
            let camposObrigatorios = [];
            let tipo = this.tab.substring(0, this.tab.length - 1);
            if (this.tab === "produtos") {
                camposObrigatorios = ['descricao', 'quantidadeEstoque', 'precoVendaFisica',
                    'precoCompra', 'precoVendaJuridica'];
            } else if (this.tab === "pessoas" && item.tipo) {
                camposObrigatorios = ['nome', 'dataNascimento'];
                tipo += '/' + item.tipo.toLowerCase();

                if (item.tipo === 'FISICA') {
                    camposObrigatorios.push('rg')
                    camposObrigatorios.push('cpf')
                } else if (item.tipo === 'JURIDICA') {
                    camposObrigatorios.push('cnpj')
                }

                if (item.cpf) {
                    item.cpf = item.cpf.replace(/\/|\.|\-/gi, '');
                }
                if (item.cnpj) {
                    item.cnpj = item.cnpj.replace(/\/|\.|\-/gi, '');
                }
            }


            if (!item || !estaPreeenchidoCamposObrigatorios(item, camposObrigatorios) || tipo === 'pessoa') {
                alert("Preencha todos os campos obrigatórios (*)!")
                return;
            }

            if (!item.id) {
                delete item.id;
                postItem(tipo, item)
                    .then(this.carregarDados)
                    .then(this.toggleModal)
                    .then(() => this.itemEditando = {})
                    .catch(() => alert('Erro ao salvar o registro, verifique os dados e tente novamente!'));
            } else {
                updateItem(tipo, item)
                    .then(this.carregarDados)
                    .then(this.toggleModal)
                    .then(() => this.itemEditando = {})
                    .catch(() => alert('Erro ao salvar o registro, verifique os dados e tente novamente!'));
            }
        },
        deletarItem(id) {
            if (!id) return;
            let tipo = this.tab.substring(0, this.tab.length - 1);
            deletar(tipo, id).then(this.carregarDados);
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
