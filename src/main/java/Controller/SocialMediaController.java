package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

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
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::postDeleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

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

    private void getAllMessageHandler(Context ctx) {
        List<Message> messages = messageService.getMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getMessageByIdHandler(Context ctx) {
        String strMessageId = ctx.pathParam("message_id");
        try {
            int messageId = Integer.parseInt(strMessageId);
            Message message = messageService.getMessageById(messageId);
            if(message != null) {
                ObjectMapper mapper = new ObjectMapper();
                ctx.json(mapper.writeValueAsString(message));
            }
            ctx.status(200);
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void postDeleteMessageByIdHandler(Context ctx) {
        String strMessageId = ctx.pathParam("message_id");
        try {
            int messageId = Integer.parseInt(strMessageId);
            Message message = messageService.deleteMessageById(messageId);
            if(message != null) {
                ObjectMapper mapper = new ObjectMapper();
                ctx.json(mapper.writeValueAsString(message));
            }
            ctx.status(200);
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void patchMessageHandler(Context ctx) {
        ObjectMapper mapper = new ObjectMapper();
        String strMessageId = ctx.pathParam("message_id");
        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            int messageId = Integer.parseInt(strMessageId);
            message.setMessage_id(messageId);

            Message updatedMessage = messageService.updateMessage(message);
            if (updatedMessage != null) {
                ctx.json(mapper.writeValueAsString(updatedMessage));
                ctx.status(200);
            } else {
                ctx.status(400); 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(400).result("Invalid JSON format");
        }
    }

    private void getMessagesByAccountIdHandler(Context ctx) {
        String strAccountId = ctx.pathParam("account_id");
        try {
            int accountId = Integer.parseInt(strAccountId);
            List<Message> messages = messageService.getMessagesByAccountId(accountId);
            ctx.json(messages);
            ctx.status(200);
        } catch(Exception e) {
            ctx.status(400);
        }
    }
}