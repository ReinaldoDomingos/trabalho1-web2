function estaPreeenchidoCamposObrigatorios(item) {
    let camposObrigatorios = ['descricao', 'quantidadeEstoque', 'precoVendaFisica',
        'precoCompra', 'precoVendaJuridica', 'idadePermitida'];

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
        msgFooter: "Easy buy 2020 &copy; Todos os direitos reservados",
        user: {nome: "Fulano de Tal", email: "email@company.com"},
        width: (innerWidth > 540) ? 'desktop' : 'mobile',
        modalAberto: false,
        editando: "",
        itemEditando: {
            id: "",
            descricao: "",
            precoCompra: "",
            precoVendaFisica: "",
            precoVendaJuridica: "",
            quantidadeEstoque: "",
        },
        itemMenuSelecionado: 'inicio',
        title: "Easy buy",
        tab: "produtos",
        produtos: [],
        modalTitle: ""
    },
    mixins: [Vue2Filters.mixin],
    created() {
        this.carregarDados()
        $(document).scroll(this.changeNavColor)
    },
    mounted() {

    },
    methods: {
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
        carregarProdutos() {
            console.log("getProdutos")
            getItens("produto")
                .then(response => {
                    if (response.status === 200)
                        if (response.data.length > 0) {
                            this.produtos = response.data;
                        } else {
                            console.log("setProdutos")
                            setProdutos()
                                .then(() => {
                                    this.carregarProdutos()
                                })
                        }
                })
        },
        carregarDados() {
            this.carregarProdutos()
        },
        mostrarTab(tab) {
            this.tab = tab
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
                console.log(item)
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
                if (!estaPreeenchidoCamposObrigatorios(this.itemEditando)) {
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
            console.log(id)
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
        }
    }
});
