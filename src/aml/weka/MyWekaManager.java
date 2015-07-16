/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.weka;

import aml.entity.Result;
import aml.global.Config;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 *
 * @author ddefalco
 */
public final class MyWekaManager {

    private Instances instances;
    private int runs;
    private int folds;
    private BufferedWriter bwr;
    private final String HEADER_RESULT_FILE = " PARAMETRO, VALORE, F-MEASURE_DT, F-MEASURE_SVM, F-MEASURE_KNN \n";
    private final String ROW_RESULT_FILE = " %s, %s, %s, %s, %s \n";


    public MyWekaManager(File dataset) {
        try {
            this.runs = Config.instance().getNumberWekaRuns();
            this.folds = 10;
            loadInstances(dataset);
            createFile();
        } catch (Exception ex) {
            Logger.getLogger(MyWekaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadInstances(File dataset) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(dataset);
        instances = loader.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
    }

    public double crossValidation(AbstractClassifier classifier, String[] options) throws Exception {
        double _fMeasure = 0;
        if (options != null) {
            classifier.setOptions(options);
        }
        for (int run = 0; run < runs; run++) {
            instances.stratify(folds);

            for (int fold = 0; fold < folds; fold++) {
                System.out.println(" run: " + run + " fold: " + fold);
                Instances train = instances.trainCV(folds, fold);
                Instances test = instances.testCV(folds, fold);

                classifier.buildClassifier(train);
                Evaluation evaluation = new Evaluation(train);
                evaluation.evaluateModel(classifier, test);
                _fMeasure += evaluation.fMeasure(0);
            }
        }

        return _fMeasure / (runs * folds);
    }

    private void createFile() throws IOException {
        /*Create the results file*/       
        File filer = new File("." + File.separator + "dbfiles" + File.separator + "WEKA_RESULTS.CSV");
        FileWriter fwr = new FileWriter(filer.getAbsoluteFile(), true);
        bwr = new BufferedWriter(fwr);
        bwr.write(HEADER_RESULT_FILE);        
    }
    
    public void test() {
        try {
            double dt = crossValidation(new J48(), null);
            double svml = crossValidation(new SMO(), null);
            double knn = crossValidation(new IBk(), new String[]{"-K", "3"});
            writeResult(new Result());
        } catch (Exception ex) {
            Logger.getLogger(MyWekaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeResult(Result r) throws IOException{
        if (bwr != null) {
            bwr.write(String.format(ROW_RESULT_FILE, r.getParamName(), r.getParamValue(), r.getDecisiontree(), r.getSvm(), r.getKnn()));
        }
    }
}
