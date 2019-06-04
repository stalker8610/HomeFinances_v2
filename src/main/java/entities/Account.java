package entities;

import javax.persistence.*;


@Entity
public class Account {

    @Id
    @Column (name = "account_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq")
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account(){}

    public Account(String name) { this.name = name; }

    @Override
    public String toString() {
        return "{"+this.id+"} "+this.name;
    }
}


