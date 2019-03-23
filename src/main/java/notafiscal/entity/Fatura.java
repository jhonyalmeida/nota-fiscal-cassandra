package notafiscal.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "invoice")

@Data
public class Fatura {

    @Id
    @Column(name = "number")
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

    @JsonIgnore
    @OneToMany(mappedBy = "fatura")
    private List<ItemFatura> itens;
    
}