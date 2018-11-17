package life.laboratory.rosbank2018;

import java.util.List;

public class Model {
    //private quatation Quatation;

    //public quatation getQuatation() {return Quatation;}
    //public void setQuatation(quatation quatation1){this.Quatation = quatation1;}
    List<quatation> Quatation;
    public List<quatation> getQuatation () {return Quatation;}
    public class quatation {
        private Double Count_sale;
        public Double getCount_sale() {
            return Count_sale;
        }

        private Double Count_purchase;
        public Double getCount_purchase() {
            return Count_purchase;
        }

        private String Quant;
        public String getQuant() {
            return Quant;
        }
    }
}
