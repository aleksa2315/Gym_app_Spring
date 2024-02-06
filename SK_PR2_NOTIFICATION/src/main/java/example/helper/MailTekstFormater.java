package example.helper;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MailTekstFormater {

    private List<String> dohvatiPostavljanjaRedosled(String message) {
        List<String> paramsPlaceholdersList = new ArrayList<>();

        int nth = 1;
        while(true)
        {
            int startIdx = 1 + StringUtils.ordinalIndexOf(message,"%",nth);
            int endIdx = StringUtils.ordinalIndexOf(message,"%",nth+1);

            if(startIdx == - 1 || endIdx == -1) break;

            String placeHolder = message.substring(startIdx,endIdx);
            paramsPlaceholdersList.add(placeHolder);
            nth += 2; //move to next placeholder
        }

        return paramsPlaceholdersList;
    }


    private List<String> getParametersInOrder(String embededMsg, Object data) throws IllegalAccessException {
        List<String> placeholderList = dohvatiPostavljanjaRedosled(embededMsg);
        System.out.println(placeholderList);
        List<String> parametersList = new ArrayList<>();

        for(String placeholder : placeholderList)
        {
            String parameter = "-1";
            for(Field classField : data.getClass().getDeclaredFields())
            {
                if(classField.getName().equals(placeholder))
                {
                    classField.setAccessible(true);
                    parameter = (String) classField.get(data);
                    break;
                }
            }
            parametersList.add(parameter);
        }

        return parametersList;
    }


    private String zameniPostavljanjeParametrom(String message, String parametar) {
        int startIdx = StringUtils.ordinalIndexOf(message,"%",1);
        int endIdx = 1 + StringUtils.ordinalIndexOf(message,"%",2);

        return message.substring(0,startIdx) + parametar + message.substring(endIdx);
    }


    public String formatirajTekst(String message, Object data) throws IllegalAccessException {
        List<String> parametersList = getParametersInOrder(message, data);
        for (String param : parametersList) {
            message = zameniPostavljanjeParametrom(message, param);
        }

        return message;
    }
}
