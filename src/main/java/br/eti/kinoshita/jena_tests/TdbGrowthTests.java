package br.eti.kinoshita.jena_tests;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TdbGrowthTests {

    private static final Model[] models = new Model[1000];
    private static final String tdbLoc = "/tmp/ramdisk/testTdb";
    private static final double bytesPerMB = 1024 * 1024;

    @BeforeClass
    public static void setup() {
        for (int i = 0; i < models.length; i++) {
            Model m = ModelFactory.createDefaultModel();
            Resource s = m
                    .createResource(String.format("http://example.com/%d", i));
            Property p = m
                    .createProperty("http://example.com/ns#somePredicate");
            m.add(m.createStatement(s, p,
                    m.createLiteral(String.format("Object value %d", i))));
            models[i] = m;
        }
    }

    @Test
    public void testGrowth() throws Exception {
        TDB.getContext().set(TDB.symUnionDefaultGraph, true);
        Dataset ds = TDBFactory.createDataset(tdbLoc);
        System.out.println(String.format("Initial TDB size: %s", getTdbSize()));
        for (int i = 0; i < 10000; i++) {
            boolean validateThisIteration = (i + 1) % 1000 == 0;
            ds.begin(ReadWrite.WRITE);
            for (int j = 0; j < models.length; j++) {
                ds.addNamedModel(String.format("http://example.com/%d", j),
                        models[j]);
            }
            ds.commit();
            if (validateThisIteration)
                validateTripleCount(ds, 1000);
            ds.begin(ReadWrite.WRITE);
            for (int j = 0; j < models.length; j++) {
                ds.removeNamedModel(String.format("http://example.com/%d", j));
            }
            ds.commit();
            if (validateThisIteration) {
                validateTripleCount(ds, 0);
                System.out.println(
                        String.format("Size of TDB after %d iterations: %s",
                                i + 1, getTdbSize()));
            }
        }
    }

    private static void validateTripleCount(Dataset ds, int expectedCount) {
        ds.begin(ReadWrite.READ);
        QueryExecution qexec = QueryExecutionFactory
                .create("SELECT (COUNT (*) AS ?count) WHERE { ?s ?p ?o }", ds);
        long count = qexec.execSelect().next().getLiteral("count").getLong();
        ds.commit();
        Assert.assertEquals(count, expectedCount);
    }

    private static String getTdbSize() {
        return String.format("%.3f MB",
                FileUtils.sizeOfDirectory(new File(tdbLoc))
                        / bytesPerMB);
    }

}
