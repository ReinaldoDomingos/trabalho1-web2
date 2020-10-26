package br.ufms.cpcx.trabalho1.entity;

import br.ufms.cpcx.trabalho1.enuns.EStatusPedido;
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
    @Column(name = "PED_STATUS", nullable = false)
    private EStatusPedido status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "PES_ID", updatable = false, insertable = false)
    private Pessoa pessoa;

    @Column(name = "PES_ID", nullable = false)
    private Long pessoaId;

    @Column(name = "PED_DATA_COMPRA", nullable = false)
    private LocalDate dataCompra;

    @Column(name = "PED_DATA_ENTREGA", nullable = false)
    private LocalDate dataEntrega;

    @Column(name = "PED_PERCENTUAL_DESCONTO", nullable = false)
    private BigDecimal percentualDesconto;
}
