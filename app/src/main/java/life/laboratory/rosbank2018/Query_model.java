package life.laboratory.rosbank2018;

public class Query_model {
    private MyQuery query;
    public MyQuery getQuery() {return query;}
    public void setQuery (MyQuery query1) {this.query = query1;}

    static public class MyQuery{
        public MyQuery () {}

        private String Session;
        public void setSession (String str) {this.Session = str;}

        private Integer From;
        public void setFrom (Integer from) {this.From = from;}

        private Integer To;
        public void setTO (Integer to) {this.To = to;}


        private String Quant;
        public void setQuant (String str) {this.Quant = str;}


        private String Action;
        public void setAction (String str) {this.Action = str;}

    }
}
