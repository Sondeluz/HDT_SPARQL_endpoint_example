A standalone Java application that serves any HDT file as a Jena-fuseki SPARQL endpoint

# Compilation
Run the `compile.sh` script. It will create a fatjar file including all dependencies.

# Usage:
`java -jar endpoint.jar -f hdt_file.hdt`

The HDT file will be memory-mapped an indexed to allow fast querying. The .index file will be automatically created and loaded if not present. It will then create a SPARQL endpoint available on `http://localhost:3330/sparql`.
