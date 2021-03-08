package com.fmi.springbootcrud.repository;

import com.fmi.springbootcrud.data.Booking;
import com.netflix.graphql.dgs.DgsComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@DgsComponent
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByClientIdOrderByBookingDateDesc(Long clientId);
}
