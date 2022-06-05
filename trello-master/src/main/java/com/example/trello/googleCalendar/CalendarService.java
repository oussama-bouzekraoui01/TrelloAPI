package com.example.trello.googleCalendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class CalendarService {
    private static final String APPLICATION_NAME = "Authorization";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "C:\\Users\\Oussama BOUZEKRAOUI\\Documents\\Deuxième_Année";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    //    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\Oussama BOUZEKRAOUI\\Downloads\\code_secret_client_466380275884-qgqa26am8knc727tfs9mc9oonm3l0v3n.apps.googleusercontent.com.json";
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\Oussama BOUZEKRAOUI\\Downloads\\code_secret_client_353866447564-u8j6qdhj34i3am7hek8av2c7j5morng9.apps.googleusercontent.com.json";

    private static Credential getCredentials(NetHttpTransport HTTP_TRANSPORT) throws IOException {

//        InputStream in = CalendareService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//
        File file = new File(CREDENTIALS_FILE_PATH);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(file));


        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;

    }


    public List<Event> getEvent(String start, String end) throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTime debut = new DateTime(start);
        DateTime fin = new DateTime(end);
        Events events = service.events().list("primary")
                .setTimeMin(debut)
                .setTimeMax(fin)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime starts = event.getStart().getDateTime();
                if (starts == null) {
                    starts = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), starts);
            }
        }
        return items;
    }



    public void addNewEvent(EventParam eventParam) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName("applicationName").build();

        Event event = new Event().setDescription(eventParam.getDescription())
                .setSummary(eventParam.getSummary())
                .setLocation(eventParam.getLocation());

        DateTime startDateTime = new DateTime(eventParam.getStart());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Africa/Casablanca");
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventParam.getEnd());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Africa/Casablanca");
        event.setEnd(end);


        List<EventAttendee> participants = new ArrayList<>();
        for(int i = 0; i < eventParam.getParticipant().length; i++) {
            participants.add(new EventAttendee().setEmail(eventParam.getParticipant()[i]));
        }

        event.setAttendees(participants);

        String calendarId = "primary";
        if(getEvent(eventParam.getStart(), eventParam.getEnd()) == null)
            event = service.events().insert(calendarId, event).execute();
        else {
            for(Event evenement : getEvent(eventParam.getStart(), eventParam.getEnd())) {
                List<EventAttendee> eventAttendees = evenement.getAttendees();
                for(int i = 0; i < participants.size(); i++){
                    for(int j = 0; j < eventAttendees.size(); j++) {
                        if(participants.get(i).getEmail().equals(eventAttendees.get(j).getEmail())) {
                            throw new IllegalStateException("Impossible d'ajouter cet evenement");
                        }
                    }
                }
            }
            event = service.events().insert(calendarId, event).execute();

        }

        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        EventParam eventParam = new EventParam("Testing","Rabat","Testing","2022-03-21T18:24:45.478+00:00","2022-03-21T20:24:45.478+00:00");
//        CalendarService calendarService = new CalendarService();
//        calendarService.addNewEvent(eventParam);
//    }

    public void deleteEvent(String eventID) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

//        for(Event event : getEvent(start, end)) {
//            String eventID = event.getId();
//            service.events().delete("primary", eventID).execute();
//        }

        service.events().delete("primary", eventID).execute();
    }

    public void updateEvent(String eventID, EventParam eventParam) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        Event event = service.events().get("primary", eventID).execute();
        event.setSummary(eventParam.getSummary());
        event.setLocation(eventParam.getLocation());
        event.setDescription(eventParam.getDescription());

        DateTime startDateTime = new DateTime(eventParam.getStart());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Africa/Casablanca");
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventParam.getEnd());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Africa/Casablanca");
        event.setEnd(end);





        List<EventAttendee> participants = new ArrayList<>();
        for(int i = 0; i < eventParam.getParticipant().length; i++) {
            participants.add(new EventAttendee().setEmail(eventParam.getParticipant()[i]));
        }
        event.setAttendees(participants);

        service.events().update("primary", event.getId(), event).execute();

    }
}
