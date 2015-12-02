package org.aksw.lodtenant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.core.SparqlServiceFactory;
import org.aksw.jena_sparql_api.core.SparqlServiceFactoryHttp;
import org.aksw.jena_sparql_api.core.utils.UpdateRequestUtils;
import org.aksw.jena_sparql_api.update.FluentSparqlServiceFactory;
import org.aksw.jena_sparql_api.utils.DatasetDescriptionUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.jena.riot.web.HttpOp;

public class TestHttpConnectionPool {
    //@Test
    public void test1() throws InterruptedException {
        PoolingClientConnectionManager connManager = new PoolingClientConnectionManager();
        connManager.setMaxTotal(50);
        connManager.setDefaultMaxPerRoute(50);

        DefaultHttpClient httpClient = new DefaultHttpClient(connManager);//new SystemDefaultHttpClient();
        //httpClient.getConnectionManager().


        //httpClient.addRequestInterceptor(logger);
        //httpClient.addResponseInterceptor(logger);

        // TODO This sets the httpClient globally, which is actually not desired
        HttpOp.setDefaultHttpClient(httpClient);
        HttpOp.setUseDefaultClientWithAuthentication(true);



        SparqlServiceFactory ssf = FluentSparqlServiceFactory.from(new SparqlServiceFactoryHttp()).create();
        final SparqlService ss = ssf.createSparqlService("http://localhost:8890/sparql", DatasetDescriptionUtils.createDefaultGraph("http://example.org/test/"), null);

        ExecutorService es = Executors.newCachedThreadPool();

        for(int j = 0; j < 5; ++j) {

            Runnable r = new Runnable() {

                @Override
                public void run() {
                    for(int i = 0; i < 1000; ++i) {
                        ss.getUpdateExecutionFactory().createUpdateProcessor(UpdateRequestUtils.parse("INSERT DATA { <a> <b> <c> }")).execute();
                        ss.getQueryExecutionFactory().createQueryExecution("CONSTRUCT WHERE { ?s ?p ?o }").execConstruct();
                        ss.getUpdateExecutionFactory().createUpdateProcessor(UpdateRequestUtils.parse("DELETE DATA { <a> <b> <c> }")).execute();
                    }
                }
            };

            es.execute(r);
        }

        es.shutdown();
        boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);

    }
}

