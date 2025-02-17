package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account addedAccount = accountService.addAccount(account);
            if (addedAccount != null) {
                ctx.json(mapper.writeValueAsString(addedAccount));
                ctx.status(200);
            } else {
                System.out.println(account.toString());
                ctx.status(400); 
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            ctx.status(400).result("Invalid JSON format");
        }
    }

    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account loginAccount = accountService.accountLogin(account);
            if (loginAccount != null) {
                ctx.json(mapper.writeValueAsString(loginAccount));
                ctx.status(200);
            } else {
                ctx.status(401); 
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            ctx.status(400).result("Invalid JSON format");
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message addedMessage = messageService.addMessage(message);
            if (addedMessage != null) {
                ctx.json(mapper.writeValueAsString(addedMessage));
                ctx.status(200);
            } else {
                ctx.status(400); 
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            ctx.status(400).result("Invalid JSON format");
        }
    }
}