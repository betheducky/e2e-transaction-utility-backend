package example.transactions;

import example.transactions.store.TransactionStore;
import example.transactions.service.TransactionService;
import example.transactions.controller.TransactionController;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        TransactionStore store = new TransactionStore();
        TransactionService service = new TransactionService(store);
        TransactionController controller = new TransactionController(service);

        HttpServer server = HttpServer.create( new InetSocketAddress(8080), 0);

        server.createContext("/api/transactions", controller);

        server.start();

        System.out.println("Server started on http://localhost:8080/api");
    }
    
}
