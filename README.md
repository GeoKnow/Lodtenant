# Lodtenant
Spring-batch based workflow toolkit for RDF and SPARQL with JSON config support. Entrust lieu- uh, lodtenant with your battle plans for conquering the Web of Data.


# In a nutshell
With lodtenant you can register workflow definitions, and later launch them (optionally with specific settings).

```bash
# Register a workflow, optionally using a name different from the file name
lodtenant ready my-workflow.json
  # returns a job instance id, such as my-workflow-0001
lodtenant aim my-workflow-0001 [with config-params.json]
lodtenant fire my-workflow-0001
lodtenant status
lodtenant query 'Select * { ?s ?p ?o }'
```

# JSON based Workflow configuration
First of all, lodtenant uses the GSON parser which is very permissive at parsing,
so it is valid to include comments (// and /* */) and use single quotes!

The configuration is done using either an ordinary spring xml file or a json version thereof.
The json version works as follows:

```
{
  // Declare the source and target sparql endpoint
  dbpedia: { $sparqlService: ['http://dbpedia/sparql', 'http://dbpedia.org'] },
  local: { $sparqlService: ['http://localhost:8890/sparql', 'http://dbpedia.org'] },

  // Create a job having two steps
  job: { $simpleJob: {
    name: 'Load DBpedia into my store',
    steps: [
      // Clear the graph
      { $sparqlUpdate: [ '#{ local }', 'DELETE { ?s ?p ?o } WHERE { ?s ?p ?o }'

      // 
      { $sparqlPipe: {
        
      } }
    ]
  } }
}
```

## JSON shortcuts

* `$sparqlService`
* `$sparqlFile`
* `$simpleJob`
* `$sparqlUpdate`
* `$sparqlPipe`
* `$sparqlStep`



## Preconfigure converters
String




