package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "resource_qualification")

@Data
public class Qualificacao {

    @Id
    @Column(name = "id_resource_qualification")
    private Integer id;

    @Column(name = "qualification_name")
    private String nome;
    
}