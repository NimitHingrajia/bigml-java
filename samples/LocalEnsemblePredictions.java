package org.bigml.binding.samples;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.bigml.binding.LocalEnsemble;
import org.bigml.binding.MissingStrategy;
import org.bigml.binding.PredictionMethod;
import org.json.simple.JSONObject;

public class LocalEnsemblePredictions {

    public static void predict(String inputFile, String outFile)
        throws Exception {
        // Use here the identifier of an ensemble you own
        String eid = "ensemble/52df49b60c0b5e589b00014b";
        BigMLClient api = new BigMLClient();
        LocalEnsemble e = new LocalEnsemble(api.getEnsemble(eid));

        // Here, the inputFile must contain a header line with either
        // the names or the identifiers of the fields.  If it doesn't,
        // you can call the CSVReader constructor that takes 3
        // arguments: the last one of them is an array of strings
        // which will be used as the header line.
        CSVReader reader = new CSVReader(inputFile, e.getFields());

        // Let's write the predictions to an output file
        FileWriter writer = new FileWriter(outFile);
        while (reader.hasNext()) {
        	JSONObject inputs = (JSONObject) reader.next();
            HashMap<String, Object> pred = 
            	e.predict(inputs, PredictionMethod.PLURALITY,
                    null, MissingStrategy.PROPORTIONAL, null, null, null, true);
        
            String pv = (String) pred.get("prediction");
            if (pv != null) writer.write(pv);
        }
        writer.close();
    }
}
