package dev.dankom.unanimous.http;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.unanimous.UnanimousServer;
import dev.dankom.unanimous.group.transaction.UTransaction;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TransactionRest {
    @PostMapping("/profile/transact")
    public void transact(String sender, String receiver, float amount, String description) {
        try {
            UnanimousServer.getInstance().getClassManager().transact(UUID.fromString(sender), UUID.fromString(receiver), amount, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/profile/transactions/{homeroom}/{id}")
    public String getTransactions(@PathVariable String homeroom, @PathVariable String id) {
        JSONArray transactions = new JSONArray();
        for (UTransaction transaction : UnanimousServer.getInstance().getClassManager().getGroup(homeroom).getTransactions()) {
            if (transaction.isMine(UUID.fromString(id))) {
                transactions.add(new JsonObjectBuilder()
                        .addKeyValuePair("id", transaction.getID())
                        .addKeyValuePair("sender", transaction.getSender())
                        .addKeyValuePair("receiver", transaction.getReceiver())
                        .addKeyValuePair("long", transaction.getTime())
                        .addKeyValuePair("amount", transaction.getAmount())
                        .addKeyValuePair("description", transaction.getDescription())
                        .build());
            }
        }
        return new JsonObjectBuilder().addArray("transactions", transactions).build().toJSONString();
    }
}
