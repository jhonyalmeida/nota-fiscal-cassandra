package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resource_qualification")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qualificacao {

    @Id
    @Column(name = "id_resource_qualification")
    private Integer id;

    @Column(name = "qualificatin_name")
    private String nome;
    
}