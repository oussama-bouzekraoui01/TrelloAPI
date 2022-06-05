package com.example.trello.googleCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/calendar")
@CrossOrigin("*")
public class CalendarController {

    @Autowired
    public final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @GetMapping(path = "{start}/{end}")
    public List<Event> getEvent(@PathVariable("start") String start, @PathVariable("end") String end) throws GeneralSecurityException, IOException {
        return calendarService.getEvent(start, end);
    }

    @PostMapping
    public void addNewEvent(@RequestBody EventParam eventParam) throws GeneralSecurityException, IOException {
        calendarService.addNewEvent(eventParam);
    }

    @DeleteMapping(path = "{eventID}")
    public void deleteEvent(@PathVariable("eventID") String eventID) throws GeneralSecurityException, IOException {
        calendarService.deleteEvent(eventID);
    }

    @PutMapping(path = "{eventID}")
    public void updateEvent(@PathVariable("eventID") String eventID, @RequestBody EventParam eventParam) throws GeneralSecurityException, IOException {
        calendarService.updateEvent(eventID, eventParam);
    }

}
