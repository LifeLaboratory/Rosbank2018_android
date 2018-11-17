package life.laboratory.rosbank2018.server;

public class Quotations {

    private Quotation[] Quotation;

    public Quotation[] getQuotation() {
        return Quotation;
    }

    public void setQuotation(Quotation[] quotation) {
        this.Quotation = quotation;
    }

    public class Quotation {

        private int id_quotation_from;
        private float Count_purchare;
        private int id_quotation_to;
        private String Name;
        private float Count_sale;

        public int getIdQuotationFrom() {
            return id_quotation_from;
        }

        public void setIdQuotationFrom(int id_quotation_from) {
            this.id_quotation_from = id_quotation_from;
        }

        public float getCountPurchare() {
            return Count_purchare;
        }

        public void setCountPurchare(float count_purchare) {
            this.Count_purchare = count_purchare;
        }

        public int getIdQuotationTo() {
            return id_quotation_to;
        }

        public void setIdQuotationTo(int id_quotation_to) {
            this.id_quotation_to = id_quotation_to;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }

        public float getCountSale() {
            return Count_sale;
        }

        public void setCountSale(float count_sale) {
            this.Count_sale = count_sale;
        }
    }
}
