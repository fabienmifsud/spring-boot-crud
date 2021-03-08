package com.fmi.springbootcrud.fetcher;

import com.fmi.springbootcrud.data.Booking;
import com.fmi.springbootcrud.data.Client;
import com.fmi.springbootcrud.data.Product;
import com.fmi.springbootcrud.repository.BookingRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@DgsComponent
public class BookingFetcher {

    @Autowired
    private BookingRepository bookingRepository;

    @DgsData(parentType = "Query", field = "bookings")
    @RolesAllowed({"ADMIN", "USER"})
    public Iterable<Booking> getAll() {
        return this.bookingRepository.findAll();
    }

    @DgsData(parentType = "Mutation", field = "createBooking")
    @RolesAllowed("ADMIN")
    public boolean createBooking(@InputArgument(value = "clientId") Long clientId,
                                 @InputArgument(value = "productId") Long productId) throws ParseException {

        Booking booking = new Booking();
        booking.setBookingDate(new Date());
        Client client = new Client();
        client.setId(clientId);
        booking.setClient(client);
        Product product = new Product();
        product.setId(productId);
        booking.setProduct(product);

        return this.bookingRepository.save(booking) != null;
    }

    @DgsData(parentType = "Query", field = "bookingsByClient")
    @RolesAllowed({"ADMIN", "USER"})
    public List<Booking> getAllByClient(@InputArgument(value = "clientId") Long clientId) {
        return this.bookingRepository.findByClientIdOrderByBookingDateDesc(clientId);
    }

}
