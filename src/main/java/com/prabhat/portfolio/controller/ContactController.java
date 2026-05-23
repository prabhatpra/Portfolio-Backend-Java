package com.prabhat.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prabhat.portfolio.dto.RequestDto;
import com.prabhat.portfolio.dto.ResponseDto;
import com.prabhat.portfolio.enums.ContactStatus;
import com.prabhat.portfolio.service.ContactService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/contacts")
@Slf4j
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // ================= POST CONTACT =================
    @PostMapping
    public ResponseEntity<ResponseDto> contact(
            @Valid @RequestBody RequestDto requestDto) {

        log.info("POST /contacts API called for email: {}",
                requestDto.getEmail());

        ResponseDto response = contactService.contactUser(requestDto);

        log.info("POST /contacts API completed successfully for email: {}",
                requestDto.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllContacts() {

        log.info("GET /contacts API called");

        List<ResponseDto> contacts = contactService.getAllContacts();

        log.info("GET /contacts completed. Total records: {}",
                contacts.size());

        return ResponseEntity.ok(contacts);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getContactById(
            @PathVariable Long id) {

        log.info("GET /contacts/{} API called", id);

        ResponseDto contact = contactService.getContactById(id);

        log.info("GET /contacts/{} completed successfully", id);

        return ResponseEntity.ok(contact);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Long id) {

        log.info("DELETE /contacts/{} API called", id);

        contactService.deleteContact(id);

        log.info("DELETE /contacts/{} completed successfully", id);

        return ResponseEntity.noContent().build();
    }

    // ================= UPDATE STATUS =================
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam ContactStatus status) {

        log.info("PATCH /contacts/{}/status called with status: {}",
                id, status);

        contactService.updateStatus(id, status);

        log.info("Status updated successfully for id: {}", id);

        return ResponseEntity.ok("Status updated successfully");
    }
}