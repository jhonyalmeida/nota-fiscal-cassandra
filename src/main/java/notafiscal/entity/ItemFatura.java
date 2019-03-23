package notafiscal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ivoice_item")

@Data
public class ItemFatura {

    @Id
    @Column(name = "invoice_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Fatura fatura;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Servico servico;

    @Column(name = "resource_id")
    private Integer idNota;

    @Column(name = "quantity")
    private Integer quantidade;

    @Column(name = "unit_value")
    private Double valorUnitario;

    @Column(name = "tax_percent")
    private Double percentualImposto;

    @Column(name = "discount_percent")
    private Double desconto;

    @Column(name = "date_of_reference")
    private LocalDate dataReferencia;

    @Column(name = "subtotal")
    private Double subtotal;

}