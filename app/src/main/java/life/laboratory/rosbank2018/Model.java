package life.laboratory.rosbank2018;

public class Model {

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
