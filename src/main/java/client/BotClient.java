package client;

import server.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client{
    public static void main(String[] args) {
        new BotClient().run();
    }

    @Override
    protected  SocketThread getSocketThread(){
        return new BotSocketThread();
    }
    @Override
    protected boolean shouldSendTextFromConsole(){
        return false;
    }
    @Override
    protected String getUserName() {
        return "date_bot_" + (int)(Math.random()*100);
    }
    public class BotSocketThread extends SocketThread{

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] split = message.split(": ");
            if (split.length != 2) return;
            String name = split[0];
            String command = split[1];
            String format = null;
            switch (command) {
                case "дата":
                    format = "d.MM.YYYY";
                    break;
                case "день":
                    format = "d";
                    break;
                case "месяц":
                    format = "MMMM";
                    break;
                case "год":
                    format = "YYYY";
                    break;
                case "время":
                    format = "H:mm:ss";
                    break;
                case "час":
                    format = "H";
                    break;
                case "минуты":
                    format = "m";
                    break;
                case "секунды":
                    format = "s";
                    break;
            }

            if (format != null) {
                String answer = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
                BotClient.this.sendTextMessage("Информация для " + name + ": " + answer);
            }
        }
    }
}
