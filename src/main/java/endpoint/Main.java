package endpoint;

import org.apache.commons.cli.*;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdtjena.HDTGraph;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // CMD input setup
        Options options = new Options();

        Option c = new Option("f", "File", true, "HDT file to load");
        c.setRequired(true);
        options.addOption(c);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(" ", options);

            System.exit(1);
        }


        // Load the HDT file
        HDT hdt = HDTManager.mapIndexedHDT(cmd.getOptionValue("f"));
        HDTGraph graph = new HDTGraph(hdt, true);

        // Wrap it with Jena
        Model model = ModelFactory.createModelForGraph(graph);
        Dataset dataset = DatasetFactory.wrap(model);

        // Set up and start the Fuseki server on http://localhost:3330/sparql
        FusekiServer server = FusekiServer.create()
                .add("/sparql", dataset)
                .build();
        server.start();
    }
}