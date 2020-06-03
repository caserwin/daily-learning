package mllib.xgboost;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

/**
 * @author yidxue
 */
public class XgbPredictInJavaDemo {
    public static void main(String[] args) {
        try {
            Booster booster = XGBoost.loadModel("data/model/xgbClassificationModel");
            float[] data = {5.4f, 3.9f, 1.3f, 1.4f, 5.7f, 3.0f, 4.2f, 1.2f, 6.9f, 3.1f, 5.1f, 2.3f};
            DMatrix dMatrix = new DMatrix(data, 3, 4);
            float[][] res = booster.predict(dMatrix);

            for (float[] dArr : res) {
                for (float d : dArr) {
                    System.out.print(d + ",");
                }
                System.out.println();
            }

        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }
    }
}
