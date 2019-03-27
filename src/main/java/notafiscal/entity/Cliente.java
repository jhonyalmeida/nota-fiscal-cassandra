package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "id_customer")
    private Integer id;

    @Column(name = "name")
    private String nome;

    @Column(name = "address")
    private String endereco;

    @Column(name = "country")
    private String pais;

}