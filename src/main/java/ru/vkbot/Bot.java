package ru.vkbot;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import com.vk.api.sdk.objects.messages.KeyboardButtonActionType;

import javax.xml.crypto.Data;

public class Bot {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException
    {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        /*
        Keyboard keyboard = new Keyboard();

        List<List<KeyboardButton>> allKey = new ArrayList<>();
        List<KeyboardButton> line1 = new ArrayList<>();
        line1.add(
                new KeyboardButton()
                        .setAction(
                                new KeyboardButtonAction()
                                        .setLabel("Привет")
                                        .setType(TemplateActionTypeNames.TEXT)
                        )
                        .setColor(KeyboardButtonColor.POSITIVE)
        );
        line1.add(
                new KeyboardButton()
                        .setAction(
                                new KeyboardButtonAction()
                                        .setLabel("Кто я?")
                                        .setType(TemplateActionTypeNames.TEXT)
                        )
                        .setColor(KeyboardButtonColor.POSITIVE)
        );
        line1.add(
                new KeyboardButton()
                        .setAction(
                                new KeyboardButtonAction()
                                        .setLabel("123")
                                        .setType(TemplateActionTypeNames.TEXT)
                        )
                        .setColor(KeyboardButtonColor.NEGATIVE)
        );
        allKey.add(line1);
        keyboard.setButtons(allKey);*/
        GroupActor actor = new GroupActor(227293121, "vk1.a.TlFSTEWmN3_LiHpIzwQkleF8qACPc51IphBJxaVcOExbC-UCD955E-7ZIBZD1x35b43Fju0mKKfbxK6lwbXgT_FZ0CB6PR-CIn4CW6VQcaiHqFAp8K0HICs9Co0GHQFfghcyERHlrQQuoiDGcgv5O4nVauM8CzCQ76tpJzJrzRQF6LPmHiOy4ayZAj0qXMlPO4rbm7kFboLq-dK0ymlNJA");
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        while (true){
            MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()){
                messages.forEach(message -> {
                    System.out.println(message.toString());
                    try {
                        if (message.getText().equals("Начать")){
                            vk.messages().send(actor).message("Напиши свою группу").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().equals("У Егора большой хуй")){
                            vk.messages().send(actor).message("Ахуеть, спасибо большое за комплимент<3").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().toUpperCase().matches("^[0-9]{2}[А-Я]{2}[0-9А-Я]{1}([0-9]{0,1})?$" )) {
                            Inform data = new Inform();
                            vk.messages().send(actor).message(data.find(message.getText().toUpperCase())).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else {
                            vk.messages().send(actor).message("Не верный формат группы").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                    }
                    catch (ApiException | ClientException | IOException e) {e.printStackTrace();}
                });
            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }
    }
}