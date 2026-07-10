package com.prabhat.portfolio.entity;

import java.time.LocalDateTime;

import com.prabhat.portfolio.enums.ContactStatus;
import com.prabhat.portfolio.enums.EmailStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "contacts",
    indexes = {
        @Index(name = "idx_contact_email", columnList = "email"),
        @Index(name = "idx_contact_status", columnList = "status"),
        @Index(name = "idx_contact_email_status", columnList = "emailStatus")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String email;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String subject;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    // Contact Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContactStatus status;

    // Email Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmailStatus emailStatus;

    // Retry Count
    @Column(nullable = false)
    private Integer emailRetryCount;

    // Last Email Failure Reason
    @Column(columnDefinition = "TEXT")
    private String emailError;

    // Admin Reply
    @Column(columnDefinition = "TEXT")
    private String replyMessage;

    private LocalDateTime readAt;

    private LocalDateTime repliedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = ContactStatus.NEW;
        }

        if (this.emailStatus == null) {
            this.emailStatus = EmailStatus.PENDING;
        }

        if (this.emailRetryCount == null) {
            this.emailRetryCount = 0;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}