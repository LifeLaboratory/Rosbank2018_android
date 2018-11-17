package life.laboratory.rosbank2018.server;

import java.util.Arrays;

public class Person {

    private Currency[] Currency;
    private String Name;

    public Person.Currency[] getCurrency() {
        return Currency;
    }

    public void setCurrency(Person.Currency[] currency) {
        this.Currency = Arrays.copyOf(currency, currency.length);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public class Currency {
        private int id_currency;
        private String name_currency;
        private float cost;


        public int getIdCurrency() {
            return id_currency;
        }

        public void setIdCurrency(int id_currency) {
            this.id_currency = id_currency;
        }

        public String getNameCurrency() {
            return name_currency;
        }

        public void setNameCurrency(String name_currency) {
            this.name_currency = name_currency;
        }

        public float getCost() {
            return cost;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }
    }
}
