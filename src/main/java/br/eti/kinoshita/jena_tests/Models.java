package br.eti.kinoshita.jena_tests;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class Models {

    public static void main(String[] args) {
        String personURI = "https://kinoshita.eti.br/Bruno";
        String fullName = "Bruno Kinoshita";
        Model model = ModelFactory.createDefaultModel();
        Resource bruno = model.createResource(personURI);
        bruno.addProperty(VCARD.FN, fullName);
        System.out.println("OK!");
    }
}
