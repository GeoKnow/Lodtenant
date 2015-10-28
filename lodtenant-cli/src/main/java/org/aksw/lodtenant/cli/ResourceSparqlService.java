package org.aksw.lodtenant.cli;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.core.utils.ServiceUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import com.hp.hpl.jena.graph.Node;

public class ResourceSparqlService
    implements WritableResource
{
    protected SparqlService sparqlService;
    protected QuadCoordinate coordinate;

    public ResourceSparqlService(SparqlService sparqlService, Node node) {
        this.sparqlService = sparqlService;

    }

    @Override
    public InputStream getInputStream() throws IOException {
      QueryExecutionFactory qef = sparqlService.getQueryExecutionFactory();
      //Node n = ServiceUtils.fetchNode(qef);


        return null;
    }

    @Override
    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReadable() {
        return true;
    }

    @Override
    public boolean isOpen() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public URL getURL() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public URI getURI() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getFile() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long contentLength() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long lastModified() throws IOException {
        //RDFNode o = model.listObjectsOfProperty(s, DCTerms.modified);

        //o.asLiteral().getValue();
        return 0l;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {


//        QueryExecutionFactory qef = sparqlService.getQueryExecutionFactory();
//        Node n = ServiceUtils.fetchNode(qef

        // TODO Auto-generated method stub
        return null;
    }
}
