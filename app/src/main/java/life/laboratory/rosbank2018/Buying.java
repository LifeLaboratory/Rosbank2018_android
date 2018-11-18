package life.laboratory.rosbank2018;

public class Buying {
    private Integer Status;
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer answer) {Status = answer; }
    private Buy_class buying;
    public Buy_class getBuying(){return buying;}

    public void setBuying(Buy_class buying) {
        this.buying = buying;
    }

    public static class Buy_class {
        private Integer From;

        public void setFrom(Integer from) {
            From = from;
        }

        public Integer getFrom() {
            return From;
        }
        private Integer To;

        public Integer getTo() {
            return To;
        }

        public void setTo(Integer to) {
            To = to;
        }
        private String Action;

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }
        private Integer Count_send;

        public Integer getCount_send() {
            return Count_send;
        }

        public void setCount_send(Integer count_send) {
            Count_send = count_send;
        }
        private String Session;

        public String getSession() {
            return Session;
        }

        public void setSession(String session) {
            Session = session;
        }
        private Double Cost_user;

        public Double getCost_user() {
            return Cost_user;
        }

        public void setCost_user(Double cost_user) {
            Cost_user = cost_user;
        }
    };



}
