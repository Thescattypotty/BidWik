package org.bidwik.bid.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bidwik.bid.Enum.EStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
    name = "bids"
)
@EntityListeners(AuditingEntityListener.class)
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Item item;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bid")
    private List<IndividualBid> individualBids;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double startPrice;

    private double currentPrice;

    @Enumerated(EnumType.STRING)
    private EStatus status;
}
