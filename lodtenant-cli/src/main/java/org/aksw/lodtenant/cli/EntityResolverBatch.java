package org.aksw.lodtenant.cli;

import org.springframework.batch.core.repository.JobRepository;

public class EntityResolverBatch
    implements EntityResolver<String, Object>
{
    protected JobRepository jobRepository;
    //protected Inver

    @Override
    public Object apply(String uri) {

        // TODO Auto-generated method stub
        return null;
    }

}
