package org.danilkha.utils;

import org.danilkha.dto.AgentInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientInfoReader {
    public static String getClientInfo(HttpServletRequest request){

        List<String> values = new ArrayList<>();
        Iterator<String> iterator = request.getHeaders("User-Agent").asIterator();
        while (iterator.hasNext()){
            values.add(iterator.next());
        }

        values.add(request.getLocalAddr());
        values.add(request.getRemoteAddr());


        return values.toString();
    }
}
