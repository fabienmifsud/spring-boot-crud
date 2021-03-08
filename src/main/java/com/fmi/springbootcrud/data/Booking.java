package com.fmi.springbootcrud.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
public class Booking {

    @Id
    @SequenceGenerator(name = "booking_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @JoinColumn(name = "client_id")
    @ManyToOne(targetEntity = Client.class)
    private Client client;

    @JoinColumn(name = "product_id")
    @ManyToOne(targetEntity = Product.class)
    private Product product;

    @Column(name = "booking_date")
    private Date bookingDate;
}
