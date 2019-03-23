package notafiscal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ivoice")

@Data
public class Fatura {

    @Id
    @Column(name = "nuber")
    private Integer numero;

    @Column(name = "status")
    private String status;

    @Column(name = "service_id")
    private Integer id_servico;

    @Column(name = "resource_id")
    private Integer nota_id;
 
    @Column(name = "emission_date")
    private LocalDate data_emissao;
    
    @Column(name = "value")
    private Double valor;

    @Column(name = "customer_id")
    private String cliente;
    
}