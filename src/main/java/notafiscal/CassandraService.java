package notafiscal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.google.common.collect.ImmutableMap;

import io.micronaut.spring.tx.annotation.Transactional;
import notafiscal.entity.Cliente;
import notafiscal.entity.Fatura;
import notafiscal.entity.ItemFatura;
import notafiscal.entity.Qualificacao;
import notafiscal.entity.Recurso;
import notafiscal.entity.Servico;

@Singleton
public class CassandraService {

    private static final String BLANK = "Not Specified";

    private static final String JPA_SELECT_QUERY = "SELECT f FROM Fatura f join fetch f.itens i";

    private static final String CASSANDRA_SELECT_QUERY = "  SELECT * FROM invoice.invoice_item "
            + "WHERE invoice_number = :number ORDER BY reference_date, service_description, resource_name";

    private static final String CASSANDRA_INSERT_QUERY = "INSERT INTO invoice_item ( "
        + "invoice_number, "
        + "invoice_total, "
        + "client_name, "
        + "client_address, "
        + "service_description, "
        + "quantity, "
        + "unit_value, "
        + "resource_name, "
        + "resource_qualifications, "
        + "tax_percent, "
        + "discount_percent, "
        + "subtotal, "
        + "reference_date "
    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Cluster cluster;

    @Transactional
    public void migrarDados() {
        final List<Fatura> faturas = entityManager
                .createQuery(JPA_SELECT_QUERY, Fatura.class).getResultList();
        try (Session session = cluster.connect("invoice")) {
            final PreparedStatement query = session.prepare(CASSANDRA_INSERT_QUERY);
            for (Fatura fatura : faturas) {
                for (ItemFatura item : fatura.getItens()) {
                    final BoundStatement bound = query.bind(
                        fatura.getNumero(),
                        fatura.getValor(),
                        fatura.getCliente().getNome(),
                        fatura.getCliente().getEndereco(),
                        item.getServico() != null ? item.getServico().getDescricao() : BLANK,
                        item.getQuantidade(),
                        item.getValorUnitario(),
                        item.getRecurso() != null ? item.getRecurso().getNome() : BLANK,
                        item.getRecurso() != null 
                            ? item.getRecurso().getQualificacoesRecurso()
                                .stream()
                                .map(qualification -> qualification.getQualificacao().getNome())
                                .collect(Collectors.toSet())
                            : Collections.emptySet(),
                        item.getPercentualImposto(),
                        item.getPercentualDesconto(),
                        item.getSubtotal(),
                        item.getDataReferencia()
                    );
                    session.execute(bound);
                }
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public Optional<Fatura> carregarFatura(Long numero) {
        try (Session session = cluster.connect("invoice")) {
            SimpleStatement query = new SimpleStatement(
                CASSANDRA_SELECT_QUERY, 
                ImmutableMap.of("number", numero)
            );
            ResultSet result = session.execute(query);
            if (result.isExhausted()) {
                return Optional.empty();
            }
            List<Row> rows = result.all();
            Fatura fatura = criarFatura(rows.get(0));
            fatura.setItens(criarItensFatura(rows));
            return Optional.of(fatura);
        } catch (Exception exception) {
            throw exception;
        }
    }

    private Fatura criarFatura(Row row) {
        Fatura fatura = new Fatura();
        fatura.setNumero(row.getLong("invoice_number"));
        fatura.setValor(row.getDouble("invoice_total"));
        fatura.setCliente(new Cliente(null, row.getString("client_name"), row.getString("client_address"), null));
        return fatura;
    }

    private List<ItemFatura> criarItensFatura(List<Row> rows) {
        return rows.stream().map(row -> {
            ItemFatura item = new ItemFatura();
            item.setQuantidade(row.getInt("quantity"));
            item.setValorUnitario(row.getDouble("unit_value"));
            item.setPercentualImposto(row.getDouble("tax_percent"));
            item.setPercentualDesconto(row.getDouble("discount_percent"));
            item.setSubtotal(row.getDouble("subtotal"));
            LocalDate date = row.getDate("reference_date");
            item.setDataReferencia(java.time.LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
            item.setServico(new Servico(null, row.getString("service_description")));
            item.setRecurso(new Recurso(
                row.getString("resource_name"),
                row.getSet("resource_qualifications", String.class).stream()
                    .map(qualificationName -> new Qualificacao(null, qualificationName))
                    .collect(Collectors.toList())
            ));
            return item;
        })
        .collect(Collectors.toList());
    }

}