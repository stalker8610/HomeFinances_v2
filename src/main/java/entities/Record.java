package entities;

import enums.RecordType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
    @SequenceGenerator(name = "record_seq")
    private int id;

    private Date date;
    @ManyToOne
    @JoinColumn(name = "budget_row_id", foreignKey = @ForeignKey(name = "budget_row_id"))
    private BudgetRow budgetRow;
    private int sum;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name =  "recipient_id")
    private Account recipient;

    public Record() {
        this.date = Calendar.getInstance().getTime();
    }

    public Record(Date date){
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BudgetRow getBudgetRow() {
        return budgetRow;
    }

    public void setBudgetRow(BudgetRow budgetRow) {
        this.budgetRow = budgetRow;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }
}
