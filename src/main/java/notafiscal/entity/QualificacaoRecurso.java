package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resource_qualification_assignement")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificacaoRecurso {

    @Id
    @Column(name = "id_qualification_assignement")
    private Integer id;

    
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Recurso recurso;


    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private Qualificacao qualificacao;

}