new Vue({
    el: "#app",
    data: {
        msgFooter: "Easy buy 2020 &copy; Todos os direitos reservados",
        user: {nome: "Fulano de Tal", email: "email@company.com"},
        width: (innerWidth > 540) ? 'desktop' : 'mobile',
        navLateralAberta: (innerWidth > 540),
        itemMenuSelecionado: 'inicio',
        title: "Easy buy",
        produtos: [],
        filtro: "",
    },
    mixins: [Vue2Filters.mixin],
    created() {
        this.carregarDados()
        $(document).scroll(this.changeNavColor)
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
        },
        navtoggle() {
            this.navLateralAberta = !this.navLateralAberta;
        },
        selectNavItem(name) {
            this.itemMenuSelecionado = name
            if (innerWidth <= 540)
                this.navtoggle()
        },
        carregarProdutos() {
            getItens("produto").then(response => {
                if (response.status === 200) {
                    if (response.data.length > 0) {
                        this.produtos = response.data;
                    } else {
                        setProdutos().then(() => {
                            this.carregarProdutos()
                        })
                    }
                }
            });
        },
        carregarDados() {
            this.carregarProdutos()
        },
        vender(produto) {
            if (produto.quantidade <= 0) return;
            getItem('produto', produto.id).then(response => {
                if (response.status === 200) {
                    let produtoExistente = response.data;
                    produtoExistente.quantidadeEstoque--;
                    updateItem("produto", produtoExistente).then(this.carregarProdutos);
                }
            });
        }
    },
    filters: {
        formatarReal(value) {
            return 'R$ ' + ((value.toString().indexOf(',') === -1) ? value + ',00' : value)
        }
    }
});