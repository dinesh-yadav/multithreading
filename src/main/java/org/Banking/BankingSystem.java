package org.Banking;

import java.util.List;
import java.util.Optional;

public interface BankingSystem {
    public boolean acceptTransfer(int timestamp, String accountId, String transferId);
    public Optional<String> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount);
    public List<String> topActivity(int timestamp, int n);
    public Optional<Integer> pay(int timestamp, String accountId, int amount);
    public Optional<Integer> deposit(int timestamp, String accountId, int amount);
    public boolean createAccount(int timestamp, String accountId);
    public boolean mergeAccounts(int timestamp, String targetAccountId, String sourceAccountId);

}
