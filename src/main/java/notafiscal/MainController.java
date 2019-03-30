package notafiscal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.types.files.StreamedFile;

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

    @Get(value = "/{numero}/documento", produces = "application/pdf")
    
    public StreamedFile download(Long numero) {
        Optional<Fatura> fatura = cassandraService.carregarFatura(numero);

        if (!fatura.isPresent()) {
            return null;
        }

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.setTemplateResolver(templateResolver);
    
        Context context = new Context();
        context.setVariable("fatura", fatura.get());
        
        final String html = templateEngine.process("pdf_template", context);

        InputStream inputStream = null;
        try {
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
            FileOutputStream output = new FileOutputStream(tempFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(output, true);
            output.flush();
            output.close();
            inputStream = new FileInputStream(tempFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new StreamedFile(inputStream, "nota_fiscal.pdf");
    }

}