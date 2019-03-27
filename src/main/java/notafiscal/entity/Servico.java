package notafiscal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_description")
    private String descricao;

}