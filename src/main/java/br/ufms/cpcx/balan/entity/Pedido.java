package br.ufms.cpcx.balan.entity;

import br.ufms.cpcx.balan.enuns.EStatusPedido;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PED_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PED_STATUS")
    private EStatusPedido status;

    @Transient
    private String statusNome;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "PES_ID", updatable = false, insertable = false)
    private Pessoa pessoa;

    @Column(name = "PES_ID")
    private Long pessoaId;

    @Column(name = "PED_DATA_COMPRA")
    private LocalDate dataCompra;

    @Column(name = "PED_DATA_ENTREGA")
    private LocalDate dataEntrega;

    @Column(name = "PED_PERCENTUAL_DESCONTO")
    private BigDecimal percentualDesconto;
}
