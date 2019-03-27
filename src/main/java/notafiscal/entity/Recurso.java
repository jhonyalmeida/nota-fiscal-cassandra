package notafiscal.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resource")

@Data
@NoArgsConstructor
public class Recurso {

    @Id
    @Column(name = "id_resource")
    private Integer id;

    @Column(name = "name")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "recurso", fetch = FetchType.EAGER)
    private List<QualificacaoRecurso> qualificacoesRecurso = new ArrayList<>();

    @JsonProperty("qualificacoes")
    public List<Qualificacao> getQualificacoes() {
        return qualificacoesRecurso.stream()
                .map(QualificacaoRecurso::getQualificacao)
                .collect(Collectors.toList());
    }
    
    public Recurso(String nome, List<Qualificacao> qualificacoes) {
        this.nome = nome;
        this.qualificacoesRecurso = qualificacoes.stream()
                .map(qualificacao -> new QualificacaoRecurso(null, this, qualificacao))
                .collect(Collectors.toList());
    }

}