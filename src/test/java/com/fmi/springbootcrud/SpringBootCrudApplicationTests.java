package com.fmi.springbootcrud;

import com.fmi.springbootcrud.fetcher.BookingFetcher;
import com.fmi.springbootcrud.fetcher.ClientFetcher;
import com.fmi.springbootcrud.fetcher.ProductFetcher;
import com.fmi.springbootcrud.repository.BookingRepository;
import com.fmi.springbootcrud.repository.ClientRepository;
import com.fmi.springbootcrud.repository.ProductRepository;
import com.fmi.springbootcrud.security.BasicSecurityConfiguration;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import graphql.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedHashMap;

@SpringBootTest(classes = {BookingRepository.class, BookingFetcher.class, ProductRepository.class, ProductFetcher.class, ClientRepository.class, ClientFetcher.class, DgsAutoConfiguration.class})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
class SpringBootCrudApplicationTests {

    @MockBean
    private BasicSecurityConfiguration conf;

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void tesTheWholeThing() {
        Boolean created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createClient {\n" +
                        "  createClient(name: \"toto2\", email: \"my.email@toto.com\", dateOfBirth: \"1986-01-17 00:12:12.000\")\n" +
                        "}",
                "data.createClient");
        Assert.assertTrue(created);

        LinkedHashMap client = dgsQueryExecutor.executeAndExtractJsonPath(
                "query readClients {\n" +
                        "  clients{\n" +
                        "    id,\n" +
                        "    name,\n" +
                        "    email,\n" +
                        "    dateOfBirth\n" +
                        "  }\n" +
                        "}",
                "data.clients[0]");

        Assert.assertTrue(client.get("id").equals("1"));
        Assert.assertTrue(client.get("name").equals("toto2"));
        Assert.assertTrue(client.get("email").equals("my.email@toto.com"));
        Assert.assertTrue(client.get("dateOfBirth").equals("1986-01-17 00:12:12.0"));

        created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createProduct {\n" +
                        "  createProduct(name: \"product\", price: 10.99)\n" +
                        "}",
                "data.createProduct");
        Assert.assertTrue(created);

        LinkedHashMap product = dgsQueryExecutor.executeAndExtractJsonPath(
                "query readProducts {\n" +
                        "  products{\n" +
                        "    id,\n" +
                        "    name,\n" +
                        "    price\n" +
                        "  }\n" +
                        "}",
                "data.products[0]");

        Assert.assertTrue(product.get("id").equals("1"));
        Assert.assertTrue(product.get("name").equals("product"));
        Assert.assertTrue(product.get("price").equals(10.99d));

        created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createBooking {\n" +
                        "  createBooking(clientId: 1, productId: 1)\n" +
                        "}",
                "data.createBooking");

        Assert.assertTrue(created);

        LinkedHashMap booking = dgsQueryExecutor.executeAndExtractJsonPath(
                "query readBookingsByClient {\n" +
                        "  bookingsByClient (clientId: 1){\n" +
                        "    product {\n" +
                        "      name\n" +
                        "    }\n" +
                        "    bookingDate\n" +
                        "  }\n" +
                        "}",
                "data.bookingsByClient[0]");

        Assert.assertTrue(((LinkedHashMap)booking.get("product")).get("name").equals("product"));
        Assert.assertTrue(booking.get("bookingDate") != null);

    }

}
