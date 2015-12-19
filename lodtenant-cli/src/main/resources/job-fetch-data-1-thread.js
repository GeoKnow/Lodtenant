{
    prefixes: { $prefixes: {
       'lgdo': 'http://linkedgeodata.org/ontology/'
    } },

    //source: { $sparqlService: ['#{ jobParameters["sourceUrl"] }'] },
    source: { $sparqlService: ['http://linkedgeodata.org/sparql', 'http://linkedgeodata.org'] },
    target: { $sparqlService: ['http://localhost:8890/sparql', 'http://linkedgeodata.org'] },

//    test: {
//        type: 'java.lang.String',
//        scope: 'step',
//        ctor: ['foo']
//    },

//    taskExecutor: {
//      type: 'org.springframework.core.task.SimpleAsyncTaskExecutor'
//    },

    taskExecutor: {
      type: 'org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor',
      corePoolSize: 1,
      maxPoolSize: 1
    },

    job: { $simpleJob: {
        name: 'fetch-data-1-thread',

        steps: [
            { $logStep: {
              name: 'logtest',
              message: '#{ jobParameters["sourceUrl"] }'
            } },

            { $sparqlPipe: {
              name: 'pipe',
              chunk: 1000,
              taskExecutor: '#{ taskExecutor }',
              throttle: 1,
              //source: '#{ source }',
              //scope: 'job',
              //source: { $sparqlService: [ '#{ jobParameters["sourceUrl"] }' ] },
              source: '#{ source }',
              target: '#{ target }',
              query: 'CONSTRUCT { ?s ?p ?o } { ?s a lgdo:City ; ?p ?o }'
            } }
        ]
    } }
}
