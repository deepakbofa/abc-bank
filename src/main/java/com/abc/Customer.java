 package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static java.lang.Math.abs;

public class Customer {
	private int custID;
    private String name;
    private List<Account> accounts;

    public Customer(int id, String name) {
    	this.custID = id;
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }


	public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
    //Aug-Deepak
    public List<Account> getAccountList()
    {    	
		return accounts;
	}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + custID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custID != other.custID)
			return false;
		return true;
	}

	//
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    public String statementForAccount(Account a) {
        String s = "";        
       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }
      
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {        	
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        //System.out.println("s=========="+s);
        return s;
    }

    private String toDollars(double d){    	
        return String.format("$%,.2f", abs(d));
    }

    
}
