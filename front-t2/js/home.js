var app = new Vue({
    el: "#app",
    data: {
        msgFooter: "Easy buy 2020 &copy; Todos os direitos reservados",
        user: {nome: "Fulano de Tal", email: "email@company.com"},
        width: (innerWidth > 540) ? 'desktop' : 'mobile',
        navLateralAberta: (innerWidth > 540) ? true : false,
        itemMenuSelecionado: 'inicio',
        title: "Easy buy",
        categorias: [],
        produtos: [],
        filtro: "",
    },
    mixins: [Vue2Filters.mixin],
    computed: {
        produtosFiltrados(produtos) {
            if (!this.filtro)
                return this.produtos

            let self = this;
            return this.produtos.filter(function (produto) {
                return produto.categoria.id == self.filtro.id
            })
        }
    },
    created() {
        this.carregarDados()
        $(document).scroll(this.changeNavColor)
    },
    methods: {
        filtar(categoria) {
            console.log(categoria)
            this.filtro = categoria
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
        },
        navtoggle() {
            console.log("navtoggle")
            this.navLateralAberta = !this.navLateralAberta;
        },
        selectNavItem(name) {
            this.itemMenuSelecionado = name
            if (innerWidth <= 540)
                this.navtoggle()
        },
        carregarProdutos() {
            console.log("getProdutos")
            getItens("produto")
                .then(response => {
                    console.log(response)
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
        vender(produto) {
            if (produto.quantidade <= 0) return;
            produto.quantidade--;
            updateItem("produto", produto)
                .then(this.carregarProdutos)
        }
    },
    filters: {
        formatarReal(value) {
            return 'R$ ' + ((value.toString().indexOf(',') == -1) ?
                value + ',00' :
                value)
        }
    }
})