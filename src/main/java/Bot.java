import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setChatId(message.getChatId().toString());
        sendMsg.setReplyToMessageId(message.getMessageId());
        sendMsg.setText(text);

        try {
            setButtons(sendMsg);
            execute(sendMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
//        if (message != null && message.hasText()) {
//            if ("/help".equals(message.getText())) {
//                sendMsg(message, "How can I help you?");
//            }
//            if ("/settings".equals(message.getText())) {
//                sendMsg(message, "What do you want to set up?");
//            }
//            if(message.getText().length()>0) {
//                try {
//                    sendMsg(message, Weather.getWeather(message.getText(), model));
//                } catch (IOException e) {
//                    sendMsg(message, "City not found!");
//                }
//            }
//
//
//        }
        switch (message.getText()) {
            case "/help":
                sendMsg(message, "How can I help you?");
                break;
            case "/settings":
                sendMsg(message, "What do you want to set up?");
                break;
            default:
                try {
                    sendMsg(message, Weather.getWeather(message.getText(), model));
                } catch (IOException e) {
                    sendMsg(message, "City not found!");
                }

        }

    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "glushkovladimirbot";
    }

    public String getBotToken() {
        return "1023201879:AAG1gS_clTnGVApegaxfesrBJiCiIzl6Diw";
    }
}