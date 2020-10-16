package br.ufms.cpcx.balan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ITEM_PEDIDO")
public class ItemPedido {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PED_ID")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "PRO_ID")
    private Produto produto;

    @Column(name = "IP_QUANTIDADE")
    private Long quantidade;
}
