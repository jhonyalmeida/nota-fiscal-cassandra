package notafiscal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

@Entity
@Table(name = "invoice_item")

@Data
public class ItemFatura {

    @Id
    @Column(name = "invoice_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Fatura fatura;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Recurso recurso;

    @Column(name = "quantity")
    private Integer quantidade;

    @Column(name = "unit_value")
    private Double valorUnitario;

    @Column(name = "tax_percent")
    private Double percentualImposto;

    @Column(name = "discount_percent")
    private Double percentualDesconto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "date_of_reference")
    private LocalDate dataReferencia;

    @Column(name = "subtotal")
    private Double subtotal;

}