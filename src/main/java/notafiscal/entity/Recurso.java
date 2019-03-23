package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "resource")

@Data
public class Recurso {

    @Id
    @Column(name = "id_resource")
    private Integer id;

    @Column(name = "name")
    private String nome;
    
}