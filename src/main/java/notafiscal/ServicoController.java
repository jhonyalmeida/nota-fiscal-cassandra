package notafiscal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.spring.tx.annotation.Transactional;
import notafiscal.entity.Servico;

@Controller("/servicos")
public class ServicoController {

    @PersistenceContext
    private EntityManager entityManager;

    public ServicoController(@CurrentSession EntityManager entityManager) { 
        this.entityManager = entityManager;
    }

    @Get
    @Transactional(readOnly = true)
    public List<Servico> findAll() {
        return entityManager
                .createQuery("SELECT s FROM Servico s", Servico.class)
                .getResultList();
    }

}