package notafiscal;

import javax.inject.Inject;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import notafiscal.entity.Fatura;

@Controller("/notas-fiscais")
public class MainController {

    @Inject
    private CassandraService cassandraService;

    @Post("/migrar")
    public HttpResponse<String> executeMigration() {
        try {
            cassandraService.migrarDados();
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpResponse.serverError("Error");
        }
        return HttpResponse.ok("Success");
    }

    @Get("/{numero}")
    public Fatura findAll(Long numero) {
        return cassandraService.carregarFatura(numero).orElse(null);
    }

}