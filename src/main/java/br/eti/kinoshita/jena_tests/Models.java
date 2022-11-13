package br.eti.kinoshita.jena_tests;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;

public class Models {

    public static void main(String[] args) {
        String personURI = "https://kinoshita.eti.br/Bruno";
        Model model = ModelFactory.createDefaultModel();

        Resource bruno = model.createResource(personURI)
                .addProperty(VCARD.FN, "Bruno Kinoshita")
                .addProperty(VCARD.N, model.createResource()
                        .addProperty(VCARD.Given, "Bruno")
                        .addProperty(VCARD.Family, "Kinoshita"));

        System.out.println("OK!");

        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
        
        model.write(System.out);
    }
}
