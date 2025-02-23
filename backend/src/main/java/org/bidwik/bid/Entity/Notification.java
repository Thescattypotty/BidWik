package org.bidwik.bid.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bidwik.bid.Enum.EType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String message;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private EType type;

    @ManyToOne(optional = false)
    private User user;

    @CreatedDate
    private LocalDateTime timeStamp;
}
