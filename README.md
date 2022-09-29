to verify:

`mvn install`

`cp target/sample-doc-enricher*.jar nuxeo/nxserver/bundles`

`curl -n localhost:8080/nuxeo/api/v1/path/default-domain -H 'enrichers-document: sample'`
