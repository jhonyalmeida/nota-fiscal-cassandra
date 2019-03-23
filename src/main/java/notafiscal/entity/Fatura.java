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
@Table(name = "ivoice")

@Data
public class Fatura {

    @Id
    @Column(name = "nuber")
    private Long numero;

    @Column(name = "status")
    private String status;
 
    @Column(name = "emission_date")
    private LocalDate dataEmissao;
    
    @Column(name = "value")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Cliente cliente;
    
}