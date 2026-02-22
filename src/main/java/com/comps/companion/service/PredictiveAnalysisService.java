package com.comps.companion.service;

import org.springframework.stereotype.Service;
import weka.classifiers.functions.LinearRegression;
import weka.core.*;
import java.util.ArrayList;

@Service
public class PredictiveAnalysisService {

    public Double predictNextValue(ArrayList<Double> history) throws Exception {
        if (history.size() < 3) return null; // Need at least 3 points to predict a trend

        // 1. Set up the data structure for Weka
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("time")); // X-axis (Reading order)
        attributes.add(new Attribute("glucose")); // Y-axis (Value)

        Instances dataset = new Instances("GlucosePredictor", attributes, history.size());
        dataset.setClassIndex(1); 

        for (int i = 0; i < history.size(); i++) {
            Instance inst = new DenseInstance(2);
            inst.setValue(attributes.get(0), i);
            inst.setValue(attributes.get(1), history.get(i));
            dataset.add(inst);
        }

        LinearRegression lr = new LinearRegression();
        lr.buildClassifier(dataset);

        Instance nextInst = new DenseInstance(2);
        nextInst.setValue(attributes.get(0), history.size());
        nextInst.setDataset(dataset);

        return lr.classifyInstance(nextInst);
    }
}