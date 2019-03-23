package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customer")

@Data
public class Cliente {

    @Id
    @Column(name = "id_customer")
    private Long id;

    @Column(name = "name")
    private String nome;

    @Column(name = "address")
    private String endereco;

    @Column(name = "country")
    private String pais;

}