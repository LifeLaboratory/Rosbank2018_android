package life.laboratory.rosbank2018;

import java.util.List;

public class Model {

//    public quatation getQuatation() {return Quatation;}
    //public void setQuatation(quatation quatation1){this.Quatation = quatation1;}
    private quatation[] Quotation;
    public quatation[] getQuatation () {return this.Quotation;}
    public class quatation {
        private Double Cost_sale;
        public Double getCountSale() {
            return Cost_sale;
        }

        private Double Cost_purchase;
        public Double getCountPurchase() {
            return Cost_purchase;
        }

        private String Quant;
        public String getQuant() {
            return Quant;
        }
    }
}
