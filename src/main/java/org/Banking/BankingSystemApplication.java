package org.Banking;

import java.util.Optional;

public class BankingSystemApplication {
    public static void main(String[] args) {
        System.out.println("This is a placeholder for BankingSystemApplication.");
        BankingSystemImpl bank = new BankingSystemImpl();
        bank.createAccount((int)System.currentTimeMillis(), "A1");
        bank.deposit((int)System.currentTimeMillis(), "A1", 500);
        bank.pay((int)System.currentTimeMillis(), "A1", 200);

        bank.createAccount((int)System.currentTimeMillis(), "A2");
        bank.deposit((int)System.currentTimeMillis(), "A2", 500);
        bank.pay((int)System.currentTimeMillis(), "A2", 100);
        System.out.println("Top activity: " + bank.topActivity((int)System.currentTimeMillis(), 2));
        for(Account acc : bank.accounts.values()) {
            System.out.println(acc);
        }

        Optional<String> transferId = bank
                .transfer((int)System.currentTimeMillis(), "A1", "A2", 100);
        System.out.println(bank.transfers);
        System.out.println(bank.accounts);
        System.out.println(transferId);
        boolean finish = bank.acceptTransfer((int)System.currentTimeMillis(), "A2", transferId.get());
        for(Account acc : bank.accounts.values()) {
            System.out.println(acc);
        }
        System.out.println(finish);
    }
}
