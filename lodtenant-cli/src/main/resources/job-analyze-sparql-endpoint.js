{
    prefixes: { $prefixes: {
       'jassa': 'http://ns.aksw.org/jassa/ontology/'
    } },

    //source: { $sparqlService: ['#{ jobParameters["sourceUrl"] }'] },
    source: { $sparqlService: ['http://localhost:8890/sparql', 'http://sparql.cc/'] },
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


            { $sparqlPipe: {
              name: 'joinSummary',
              chunk: 1000,
              //source: '#{ source }',
              //scope: 'job',
              //source: { $sparqlService: [ '#{ jobParameters["sourceUrl"] }' ] },
              source: '#{ source }',
              target: '#{ target }',
              query: 'CONSTRUCT { ?x jassa:joinsWith ?y } { SELECT DISTINCT ?x ?y { ?a ?x [ ?y ?b ] } }'
            } }
        ]
    } }
}
