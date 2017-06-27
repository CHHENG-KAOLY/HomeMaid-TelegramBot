import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import processesDisp.MegaKillaSupaOvner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class myMaid extends TelegramLongPollingBot{
    public static String msg, botToken;
    public static int chatID, i = 0;
    static TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

    public static void main(String[] args) throws Exception {


        IDandTokenSaver IDaTS = new IDandTokenSaver();
        Scanner scanner;

        if (IDaTS.isEmpty()) {
            scanner = new Scanner(System.in);
            System.out.print("Enter the token: ");
            botToken = scanner.nextLine();
            botLaunch();
            System.out.println("Write a bot in Telegram. Then you will get your ID in the console.");
            while (i == 0) {}
            Thread.sleep(2000);
            System.out.print("Enter your chat ID: ");
            chatID = scanner.nextInt();
            PrintWriter out = new PrintWriter(IDaTS.file);

            out.println(botToken);
            out.print(chatID);
            out.close();
        } else {
            scanner = new Scanner(IDaTS.file);
            botToken = scanner.nextLine();
            chatID = scanner.nextInt();
            System.out.println("The token - " + botToken);
            System.out.println("Chat ID - " + chatID);
            botLaunch();
        }
        System.out.println("The bot is ready to work. Write the bot 'Help' to get a list of commands.");

    }

    public static void botLaunch() throws Exception {
        System.out.print("Home Maid launching: ");
        ApiContextInitializer.init();
        telegramBotsApi.registerBot(new myMaid());
        System.out.println("SUCCESS!");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

       if (message.getChatId() == chatID) {
            sendMessage.setChatId(Integer.toString(chatID));
            msg = message.getText();
            String killCheck = msg + "   ";

            if (message.hasPhoto()){
                //code 
            } else if (killCheck.substring(0,4).equals("Kill")) {
                MegaKillaSupaOvner megaKillaSupaOvner = new MegaKillaSupaOvner();
                String process = msg.substring(5);
                System.out.println(process + " is killed.");
                try {
                    megaKillaSupaOvner.kill(process);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send(sendMessage, process + " is killed, master!~");
            } else {
                messageSwitcher(sendMessage);
            }

        } else {
            sendMessage.setChatId(message.getChatId().toString());
            i = 1;
            send(sendMessage, "You are not my master...");
            System.out.println("Message from unknown user. Id: " + message.getChatId().toString());
        }
    }

    public void messageSwitcher (SendMessage sendMessage) {
        switch (msg) {
            case "Hi":
                send(sendMessage, "Hello, master!~");
                break;
            case "You can rest":
                send(sendMessage, "Thank you, master!~");
                System.exit(0);
                break;
            case "Help":
                send(sendMessage, "Hi - to say hello" + "\n" +
                        "You can rest - turn bot off" + "\n" +
                        "Kill 'process name' - a process killing in Windows OS, f.e. 'Kill cmd.exe'" + "\n" +
                        "Other orders in development, master.~");
                break;
            default:
                send(sendMessage, "I can't understand you..");
                System.out.println("Incorrect order: " + msg);
                break;
        }
    }

    public void send(SendMessage sendMessage, String text){
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Home Maid";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
