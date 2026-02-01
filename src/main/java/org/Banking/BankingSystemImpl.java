package org.Banking;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.atomic.*;

class BankingSystemImpl implements BankingSystem {

    Map<String, Account> accounts;
    private Long transferNumber = 1L;
    Map<String, Transfer> transfers = new HashMap<>();
    public BankingSystemImpl() {
        // TODO: implement
        accounts = new HashMap<>();
    }

    // TODO: implement interface methods here
    @Override
    public boolean createAccount(int timestamp, String accountId) {
        // default implementation
        if (accounts.containsKey(accountId)) {
            return false;
        }
        accounts.put(accountId, new Account(accountId, timestamp));
        return true;
    }

    @Override
    public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
        if (accounts.containsKey(accountId)) {
            Account acc = accounts.get(accountId);
            acc.balance += amount;
            acc.totalValueOfTransactions += amount;
            return Optional.of(acc.balance);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> pay(int timestamp, String accountId, int amount) {
        // default implementation
        if (!accounts.containsKey(accountId) || accounts.get(accountId).balance < amount) {
            return Optional.empty();
        }

        Account acc = accounts.get(accountId);
        acc.balance -= amount;
        acc.totalValueOfTransactions += amount;
        return Optional.of(acc.balance);
    }

    @Override
    public List<String> topActivity(int timestamp, int n) {
        // default implementation
        if (accounts.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result =
                accounts.values().stream()
                        .sorted((a, b) -> {
                            if (a.totalValueOfTransactions == b.totalValueOfTransactions) {
                                return a.accountId.compareTo(b.accountId);
                            }
                            return Long.compare(b.totalValueOfTransactions,
                                    a.totalValueOfTransactions);
                        }).limit(n).map(x -> x.accountId + "(" + x.totalValueOfTransactions +")").collect(Collectors.toList());

        return result;
    }

    @Override
    public Optional<String> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        if (sourceAccountId != null &&
                sourceAccountId.equals(targetAccountId))
            return Optional.empty();

        if (!accounts.containsKey(sourceAccountId) ||
                !accounts.containsKey(targetAccountId)) {
            return Optional.empty();
        }

        Account src = accounts.get(sourceAccountId);
        if (src.balance < amount) {
            return Optional.empty();
        }

        String transferId = "transfer" + transferNumber;
        transferNumber++;

        Account target = accounts.get(targetAccountId);
        transfers.put(transferId, new Transfer(timestamp, amount, src.accountId, target.accountId));
        src.balance -= amount;

        return Optional.of(transferId);


    }

    @Override
    public boolean acceptTransfer(int timestamp, String accountId, String transferId) {
        if (!transfers.containsKey(transferId))
            return false;

        if (!accounts.containsKey(accountId)) {
            return false;
        }
        Transfer transfer = transfers.get(transferId);
        if (transfer.accepted == status.ACCEPTED)
            return false;

        if (transfer.accepted == status.EXPIRED)
            return false;

        if (!transfer.targetId.equals(accountId)) {
            return false;
        }
        if (transfer.accepted == status.INPROCESS &&
                transfer.isExpired(timestamp)) {
            transfer.accepted = status.EXPIRED;
            accounts.get(transfer.srcId).balance += transfer.amount;
            return false;
        }

        transfer.accepted = status.ACCEPTED;
        accounts.get(transfer.srcId).balance += transfer.amount;
        pay(timestamp, transfer.srcId, transfer.amount);
        deposit(timestamp, transfer.targetId, transfer.amount);
        return true;
    }

    @Override
    public boolean mergeAccounts(int timestamp, String targetAccountId, String sourceAccountId) {
        if (targetAccountId != null &&
                targetAccountId.equals(sourceAccountId))
            return false;

        if (!accounts.containsKey(sourceAccountId) ||
                !accounts.containsKey(targetAccountId)) {
            return false;
        }

        Account src = accounts.get(sourceAccountId);
        Account target = accounts.get(targetAccountId);
        target.balance += src.balance;
        target.totalValueOfTransactions += src.totalValueOfTransactions;
        accounts.remove(sourceAccountId);
        for (Transfer transfer: transfers.values()) {
            if (transfer.accepted == status.EXPIRED || transfer.accepted == status.ACCEPTED) {
                continue;
            }
            if (transfer.srcId.equals(sourceAccountId)) {
                transfer.srcId = targetAccountId;
            }
            if (transfer.targetId.equals(sourceAccountId)) {
                transfer.targetId = targetAccountId;
            }
        }
        return true;
    }
}

class Account {
    String accountId;
    int balance;
    int createdAt;
    long totalValueOfTransactions;

    Account(String accountId, int timestamp) {
        this.accountId = accountId;
        this.createdAt = timestamp;
        this.balance = 0;
        this.totalValueOfTransactions = 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", totalValueOfTransactions=" + totalValueOfTransactions +
                '}';
    }
}

enum status {ACCEPTED, EXPIRED, INPROCESS};
class Transfer {

    int timestamp;
    int amount;
    String srcId;
    String targetId;
    status accepted;

    Transfer(int timestamp, int amount,
             String srcId, String targetId) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.srcId = srcId;
        this.targetId = targetId;
        this.accepted = status.INPROCESS;
    }

    boolean isExpired(int currentTime) {
        if (currentTime >= this.timestamp + 86400000)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "timestamp=" + timestamp +
                ", amount=" + amount +
                ", srcId='" + srcId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", accepted=" + accepted +
                '}';
    }
}
