package notafiscal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ivoice_item")

@Data
public class Item_fatura {

    @Id
    @Column(name = "invoice_item_id")
    private Integer id;

    @Column(name = "invoice_id")
    private Integer fatura_id;

    @Column(name = "service_id")
    private Integer id_servico;

    @Column(name = "resource_id")
    private Integer nota_id;

    @Column(name = "quantity")
    private Integer quantidade;

    @Column(name = "unit_value")
    private Double valor_unitario;

    @Column(name = "tax_percent")
    private Double taxa_perc;

    @Column(name = "discount_percent")
    private Double desconto;

    @Column(name = "date_of_reference")
    private LocalDate data;

    @Column(name = "subtotal")
    private Double subtotal;

}