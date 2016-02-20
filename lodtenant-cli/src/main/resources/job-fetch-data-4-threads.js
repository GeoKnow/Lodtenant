{
    prefixes: { $prefixes: {
       'fp7o': 'http://fp7-pp.publicdata.eu/ontology/'
    } },

    //http://cstadler.aksw.org/data/fp7/sparql
    source: { $sparqlService: ['http://cstadler.aksw.org/data/fp7/sparql', 'http://fp7-pp.publicdata.eu/'] },
    target: { $sparqlService: ['http://localhost:8890/sparql', 'http://fp7-pp.publicdata.eu/'] },

    taskExecutor: {
      type: 'org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor',
      corePoolSize: 8,
      maxPoolSize: 8
    },

    fetchQuery: 'CONSTRUCT { ?s ?p ?o } { ?s ?p ?o }',

    job: { $simpleJob: {
        name: 'fetch-data-4-threads-foobar',

        steps: [
            { $sparqlUpdate: {
                name: 'clearTargetGraph',
                target: '#{ target }',
                update: 'DELETE WHERE { ?s ?p ?o }'
            } },


            { $sparqlPipe: {
                name: 'pipe',
                chunk: 1000,
                taskExecutor: '#{ taskExecutor }',
                throttle: 8,
                source: '#{ source }',
                target: '#{ target }',
                query: '#{ fetchQuery }'
            } }
        ]
    } }
}
