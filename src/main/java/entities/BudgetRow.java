package entities;

import enums.RecordType;

import javax.persistence.*;

@Entity
public class BudgetRow {

    @Enumerated
    private RecordType recordType;
   private String name;

   @Id
   @Column(name = "budget_row_id")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_row_seq")
   @SequenceGenerator(name = "budget_row_seq")
   private int id;

   BudgetRow(RecordType recordType, String name){
      this.name = name;
      this.recordType = recordType;
   }

   BudgetRow(String name){
       this(RecordType.COST, name);
   }

   public static BudgetRow costBudgetRow(String name){
       return new BudgetRow(name);
   }

   public static BudgetRow incomeBudgetRow(String name){
       return new BudgetRow(RecordType.INCOME, name);
   }

   public static BudgetRow movementBudgetRow(String name){
        return new BudgetRow(RecordType.MOVEMENT, name);
    }

   BudgetRow(){}



    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{"+this.id+"} "+this.name+" ("+ recordType.toString()+")";
    }
}

