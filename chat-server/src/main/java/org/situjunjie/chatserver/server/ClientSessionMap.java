package org.situjunjie.chatserver.server;

import org.situjunjie.chatcommon.client.ClientSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ClientSessionMap
 * @description: 服务器中维护clientsession等容器
 * @author: situjunjie
 * @date: 2022/3/20
 **/
public class ClientSessionMap {

    private static final Map<String, List<ClientSession>> sessionMap = new HashMap();

    public static void putSession(String userId,ClientSession session){
        List<ClientSession> clientSessions = sessionMap.get(userId);
        if(clientSessions == null){
            clientSessions = new ArrayList<>();
            clientSessions.add(session);
            sessionMap.put(userId,clientSessions);
        }
    }

    public static final Map<String,List<ClientSession>> getInstance(){
        return sessionMap;
    }

    public static List<ClientSession> getSessionList(String sessionId){
        return sessionMap.get(sessionId);
    }

    public static Integer count(){
        return  sessionMap.size();
    }


}
