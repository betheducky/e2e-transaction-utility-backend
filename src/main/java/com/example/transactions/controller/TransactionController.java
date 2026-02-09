package example.transactions.controller;

import example.transactions.service.TransactionService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import example.transactions.util.JsonUtil;
import java.util.Map;
import example.transactions.enums.Type;
import example.transactions.model.Transaction;
import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;

public class TransactionController implements HttpHandler {
    
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        addCorsHeaders(exchange);

        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:4200");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
    }

    public void handle(HttpExchange exchange) throws IOException {

        try {

            addCorsHeaders(exchange);
        
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            System.out.println("METHOD: " + method);
            System.out.println("PATH: " + path);
            System.out.println("RAW URI: " + exchange.getRequestURI());

            if(method.equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if(method.equals("POST") && path.equals("/api/transactions")) {
                handleCreate(exchange);
            } else if (method.equals("GET") && path.equals("/api/transactions")) {
                handleGetAll(exchange);
            } else if(method.equals("GET") && path.startsWith("/api/transactions/")){
                handleGetById(exchange);
            } else if (method.equals("PUT") && path.startsWith("/api/transactions/")) {
                handleUpdate(exchange);
            } else if (method.equals("DELETE") && path.startsWith("/api/transactions/")) {
                handleDelete(exchange);
            } else {
                sendResponse(exchange, 404, "Not Found");
            }

        } catch (IllegalArgumentException e) {
            sendResponse(exchange, 400, e.getMessage());
        } catch (Exception e) {
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> data = JsonUtil.parse(body);

        String description = (String) data.get("description");
        double amount = Double.parseDouble(data.get("amount"));
        Type type = Type.valueOf((String) data.get("type"));

        Transaction created = service.create(description, amount, type);

        sendResponse(exchange, 201, JsonUtil.stringify(created));
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Transaction> transactions = service.get();
        sendResponse(exchange, 200, JsonUtil.stringify(transactions));
    }

    private void handleGetById(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String id = path.substring(path.lastIndexOf("/") + 1);

        Transaction item = service.getById(id);

        sendResponse(exchange, 201, JsonUtil.stringify(item));
    }

    private void handleUpdate(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String id = path.substring(path.lastIndexOf("/") + 1);

        String body = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> data = JsonUtil.parse(body);

        String desc = (String) data.get("description");
        double amount = data.get("amount") != null
            ? Double.parseDouble(data.get("amount"))
            // Zero used as null value
            : 0;
        
        Type type = data.get("type") != null
            ? Type.valueOf((String) data.get("type"))
            : null;

        Transaction updated = service.update(id, desc, amount, type);

        sendResponse(exchange, 200, JsonUtil.stringify(updated));
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String id = path.substring(path.lastIndexOf("/") + 1);

        service.delete(id);

        addCorsHeaders(exchange);
        exchange.sendResponseHeaders(204, -1);
    }
}
