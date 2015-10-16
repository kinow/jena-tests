package br.eti.kinoshita.jena_tests;

import org.apache.jena.fuseki.cmd.FusekiCmd;

public class RunFuseki {

    public static void main(String[] args) {
        FusekiCmd.main(new String[] {"--mem", "--update", "/ds"});
    }
    
}
