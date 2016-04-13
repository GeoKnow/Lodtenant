{
    prefixes: { $prefixes: {
       'jassa': 'http://ns.aksw.org/jassa/ontology/'
    } },

    taskExecutor: {
      type: 'org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor',
      corePoolSize: 1,
      maxPoolSize: 1
    },

    //source: { $sparqlService: ['#{ jobParameters["sourceUrl"] }'] },
    source: { $sparqlFile: '#{ jobParameters["sourceUrl"] }' },
    //source: { $sparqlService: ['http://localhost:8890/sparql', 'http://sparql.cc/'] },
    //source: { $sparqlFile: '/home/raven/Projects/Eclipse/Spark-RDF/tmp/fp7_ict_project_partners_database_2007_2011.nt' },
    tmp: { $sparqlService: ['mem://foobar', 'http://tmp'] },
    target: { $sparqlService: ['http://localhost:8890/sparql', 'http://sparql.cc/'] },

//    test: {
//        type: 'java.lang.String',
//        scope: 'step',
//        ctor: ['foo']
//    },


    job: { $simpleJob: {
        name: 'analysisJob',

        steps: [
            { $logStep: {
              name: 'logtest',
              message: '#{ jobParameters["sourceUrl"] }'
            } },

            // TODO This step must always be repeated...
            { $sparqlPipe: {
                name: 'loadFileIntoMemory',
                query: 'CONSTRUCT WHERE { ?s ?p ?o }',
                chunk: 10000000,
                source: '#{ source }',
                target: '#{ tmp }'
            } },

            { $sparqlPipe: {
              name: 'joinSummary',
              chunk: 10000000,
              //source: '#{ source }',
              //scope: 'job',
              //source: { $sparqlService: [ '#{ jobParameters["sourceUrl"] }' ] },
              source: '#{ tmp }',
              target: '#{ target }',
              query: 'CONSTRUCT { ?x jassa:joinsWith ?y } { SELECT DISTINCT ?x ?y { ?a ?x [ ?y ?b ] } }'
            } }
        ]
    } }
}
