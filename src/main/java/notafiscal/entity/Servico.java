package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "service")

@Data
public class Servico {

    @Id
    @Column(name = "service_id")
    private Integer id;

    @Column(name = "service_description")
    private String descricao;

}