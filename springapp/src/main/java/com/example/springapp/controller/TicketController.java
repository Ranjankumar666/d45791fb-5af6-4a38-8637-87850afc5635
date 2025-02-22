package com.example.springapp.controller;

import com.example.springapp.model.Ticket;
import com.example.springapp.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(params="eventId")
    public ResponseEntity<List<Ticket>> getTicketByEventId(@RequestParam("eventId") Long eventId) {
        List<Ticket> tickets = ticketService.getTicketByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{attendeeId}")
    public ResponseEntity<List<Ticket>> getTicketByAttendeeId(@RequestParam("attendeeId") Long attendeeId) {
        List<Ticket> tickets = ticketService.getTicketByAttendeeId(attendeeId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTicket();
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("id") Long id, @RequestBody Ticket ticket) {
        Ticket existingTicket = ticketService.getTicketById(id);
        if (existingTicket != null) {
            ticket.setId(id);
            Ticket updatedTicket = ticketService.updateTicket(ticket);
            return ResponseEntity.ok(updatedTicket);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
        boolean isDeleted = ticketService.deleteTicket(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Additional methods based on your requirements
}
