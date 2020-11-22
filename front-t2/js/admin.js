var app = new Vue({
    el: "#app",
    data: {
        msgFooter: "Easy buy 2020 &copy; Todos os direitos reservados",
        user: {nome: "Fulano de Tal", email: "email@company.com"},
        width: (innerWidth > 540) ? 'desktop' : 'mobile',
        modalAberto: false,
        editando: "",
        itemEditando: {
            id: "",
            nome: "",
            categoria: {id: ""},
            custo: "",
            valorDeVenda: "",
            quantidade: "",
            descricao: "",
            urlImg: ""
        },
        itemMenuSelecionado: 'inicio',
        title: "Easy buy",
        tab: "produtos",
        categorias: [],
        produtos: [],
        modalTitle: ""
    },
    mixins: [Vue2Filters.mixin],
    computed: {},
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
                    if (response.status == "200")
                        if (response.data.length > 0) {
                            this.produtos = response.data
                        } else {
                            console.log("setProdutos")
                            setProdutos()
                                .then(response => {
                                    this.carregarProdutos()
                                })
                        }
                })
        },
        carregarDados() {
            console.log("getCategorias")
            getItens("categoria")
                .then(response => {
                    console.log(response)
                    if (response.status = "200")
                        if (response.data.length > 0) {
                            this.categorias = response.data
                            this.carregarProdutos()
                        } else {
                            console.log("setCategorias")
                            setCategorias()
                                .then(response => {
                                    this.carregarDados()
                                })
                        }
                })
        },
        mostrarTab(tab) {
            this.tab = tab
        },
        adicionarItem() {
            this.toggleModal()
            if (this.tab == "produtos") {
                this.editando = ""
                this.modalTitle = "Novo Produto"
                this.itemEditando = {
                    id: "",
                    nome: "",
                    categoria: {id: ""},
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
                    categoria: {id: ""},
                    custo: "",
                    valorDeVenda: "",
                    quantidade: "",
                    descricao: "",
                    urlImg: ""
                }
                this.editando = "nova-categoria"
                this.modalTitle = "Nova Categoria"
            }
        },
        editarItem(item) {
            this.toggleModal()
            if (this.tab == "produtos") {
                this.itemEditando = item
                this.modalTitle = "Editar Produto"
            } else {
                console.log(item)
                this.itemEditando = {
                    id: item.id,
                    nome: item.nome,
                    categoria: {id: ""},
                    tipoCategoria: item.tipoCategoria,
                    custo: "",
                    valorDeVenda: "",
                    quantidade: "",
                    descricao: item.descricao,
                    urlImg: ""
                }
                this.modalTitle = "Editar Categoria"
            }
        },
        salvarItem() {
            if (this.tab == "produtos") {
                if (!this.itemEditando.nome || !this.itemEditando.categoria.id || !this.itemEditando.quantidade
                    || !this.itemEditando.custo || !this.itemEditando.valorDeVenda) {
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
                            categoria: {id: ""},
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
                            categoria: {id: ""},
                            custo: "",
                            valorDeVenda: "",
                            quantidade: "",
                            descricao: "",
                            urlImg: ""
                        })
            } else {
                if (!this.itemEditando.nome || !this.itemEditando.tipoCategoria) {
                    alert("Preencha todos os campos obrigatórios (*)!")
                    return
                }
                let item = {
                    nome: this.itemEditando.nome,
                    descricao: this.itemEditando.descricao,
                    tipoCategoria: this.itemEditando.tipoCategoria
                };
                if (!this.itemEditando.id) {
                    postItem("categoria", item)
                        .then(this.carregarDados)
                        .then(this.toggleModal)
                        .then(this.itemEditando = {
                            id: "",
                            nome: "",
                            categoria: {id: ""},
                            custo: "",
                            valorDeVenda: "",
                            quantidade: "",
                            descricao: "",
                            urlImg: ""
                        })
                } else {
                    item.id = this.itemEditando.id
                    updateItem("categoria", item)
                        .then(this.carregarDados)
                        .then(this.toggleModal)
                        .then(this.itemEditando = {
                            id: "",
                            nome: "",
                            categoria: {id: ""},
                            custo: "",
                            valorDeVenda: "",
                            quantidade: "",
                            descricao: "",
                            urlImg: ""
                        })
                }
            }
        },
        deletarItem(id) {
            console.log(id)
            if (!id) return;
            if (this.tab == "produtos") {
                deletar("produto", id)
                    .then(this.carregarProdutos)
            } else if (this.tab == "categorias") {
                deletar("categoria", id)
                    .then(this.carregarDados)
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
            return 'R$' + ((value.toString().indexOf(',') == -1) ?
                value + ',00' :
                value)
        }
    }
})