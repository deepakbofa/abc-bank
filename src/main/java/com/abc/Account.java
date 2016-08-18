package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private  static int incrementalAccountNumber=0;
    
    private String accountNum;
    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        incrementalAccountNumber ++;
        if(accountType ==0)
        {
        	 accountNum = "C00"+incrementalAccountNumber;
        }else if(accountType ==1)
        {
        	 accountNum = "S00"+incrementalAccountNumber;
        }else if(accountType ==2)
        {
        	 accountNum = "M00"+incrementalAccountNumber;
        }
       
    }
    
    public String getAccountNum(){
        return accountNum;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}

public double balanceCHK () {

       	return sumTransactions();
}

    public double interestEarned() {
    	
    	
        double amount = sumTransactions();
        switch(accountType){
        
        	case CHECKING:
        		return amount * 0.001;
        	
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;

            case MAXI_SAVINGS:
            	if(chkMaxiWithdraw())
            	{
            		 return amount * 0.05;
            	}
            	else
            	{
            		 return amount * 0.001;
            	}
            //    if (amount <= 1000)
             //       return amount * 0.02;
              //  if (amount <= 2000)
               //     return 20 + (amount-1000) * 0.05;
                
              //  return 70 + (amount-2000) * 0.1;            
            
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public boolean chkMaxiWithdraw ()
    {
    	 for (Transaction t: transactions)
    	 {
    	 try {
    		 if(t.amount < 0)
    		 {  long diff = DateProvider.getInstance().now().getTime() - t.transactionDate.getTime();    		    
    		    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >=10)
    		    {
    		    	 System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    		    	return true;
    		    }
    		    else
    		    {
    		    	return false;
    		    }
    		 }
    		} catch (Exception e) {
    		
    		    System.out.println("Days calculation Error.."+e);
    		 //  throw  new Exception ("Days calculation Error..");
    		}
    	 }
    	return false;
    }
    
    public double dailyAccrueInterest(double amount, int acctType)
    {
    	double annumInterestAmount =0.0;
    	 switch(accountType){
         
     	case CHECKING:
     		annumInterestAmount= amount * 0.001;
     	
         case SAVINGS:
             if (amount <= 1000)
            	 annumInterestAmount = amount * 0.001;
             else
            	 annumInterestAmount=  1 + (amount-1000) * 0.002;

         case MAXI_SAVINGS:
         	if(chkMaxiWithdraw())
         	{
         		annumInterestAmount = amount * 0.05;
         	}
         	else
         	{
         		annumInterestAmount = amount * 0.001;
         	}
   
    	 }
    	 		amount = amount + (annumInterestAmount/365); // final amount = principle + interest(daily)
    	 		return amount;
    }

}
