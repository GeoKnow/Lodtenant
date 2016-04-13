{
    prefixes: { $prefixes: {
       'lgdo': 'http://linkedgeodata.org/ontology/'
    } },

//    source: { $sparqlService: ['http://linkedgeodata.org/sparql', 'http://linkedgeodata.org'] },
//    target: { $sparqlService: ['http://localhost:8890/sparql', 'http://linkedgeodata.org'] },

    source: { $sparqlService: ['http://fp7-pp.publicdata.eu/sparql', 'http://fp7-pp.publicdata.eu/'] },
    target: { $sparqlService: ['http://localhost:8890/sparql', 'http://fp7-pp.publicdata.eu/'] },

//    test: {
//        type: 'java.lang.String',
//        scope: 'step',
//        ctor: ['foo']
//    },

    taskExecutor: {
      type: 'org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor',
      corePoolSize: 8,
      maxPoolSize: 8
    },

    test: 'baraoeuuieuouo',
    //fetchQuery: 'CONSTRUCT { ?s ?p ?o } { ?s a lgdo:City ; ?p ?o }',
    fetchQuery: 'CONSTRUCT { ?s ?p ?o } { ?s ?p ?o }',

    job: { $simpleJob: {
        name: 'fetch-data-4-threads',

        steps: [
//            { $sparqlCount: {
//                name: 'countStep',
//                target: '#{ source }',
//                query: '#{ fetchQuery }',
//                key: 'fetchQueryCount'
//            } },
//
//            { $logStep: {
//                name: 'logStep',
//                text: '#{ jobExecutionContext[fetchQueryCount] }'
//            } },
//
//            { $logStep: {
//                name: 'logStep2',
//                text: 'yay'
//            } },

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
