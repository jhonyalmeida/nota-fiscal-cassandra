package notafiscal;

import javax.inject.Singleton;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;

@Singleton
class ClusterBuilderListener implements BeanCreatedEventListener<Cluster.Builder> {

    private static final String USERNAME = "cassandra";
    private static final String PASSWORD = "cassandra";

    @Override
    public Cluster.Builder onCreated(BeanCreatedEvent<Cluster.Builder> event) {
        final Cluster.Builder builder = event.getBean();
        builder.getConfiguration().getCodecRegistry().register(LocalDateCodec.instance);
        return builder.withCredentials(USERNAME, PASSWORD);
    }
}