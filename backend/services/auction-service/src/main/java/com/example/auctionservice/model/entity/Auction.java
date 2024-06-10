package com.example.auctionservice.model.entity;

import com.example.auctionservice.model.enums.AuctionStatus;
import com.example.auctionservice.repository.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private String lotName;
    private String lotDesc;
    private Integer minPrice;
    private Integer priceStep;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    @Column(length = 1000)
    @Convert(converter = StringListConverter.class)
    private List<String> imgNames = new ArrayList<>();

    @OneToMany(mappedBy = "auction")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Bid> bids;

    @OneToMany(mappedBy = "auction")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ChatMessage> chatMessages;

}
