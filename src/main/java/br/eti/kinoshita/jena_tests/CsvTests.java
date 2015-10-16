package br.eti.kinoshita.jena_tests;

import org.apache.jena.propertytable.graph.GraphCSV;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;

public class CsvTests {

    public static void main(String[] args) throws Exception {
        Model model_csv_array_impl = ModelFactory
                .createModelForGraph(new GraphCSV("/tmp" + "/item.dat")); // PropertyTableArrayImpl
        Dataset dataset = TDBFactory.createDataset("/tmp");
        dataset.begin(ReadWrite.WRITE);
        dataset.addNamedModel("http://example/table1", model_csv_array_impl);
        dataset.commit();
    }

}
