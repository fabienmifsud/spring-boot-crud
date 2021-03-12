package com.fmi.springbootcrud;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

@SpringBootTest
@EnableAutoConfiguration
class SpringBootCrudApplicationTests {

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void tesTheWholeThing() {
        Boolean created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createClient {\n" +
                        "  createClient(name: \"toto2\", email: \"my.email@toto.com\", dateOfBirth: \"1986-01-17 00:12:12.000\")\n" +
                        "}",
                "data.createClient");
        Assertions.assertThat(created).isTrue();

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


        Assertions.assertThat(client.get("id")).isEqualTo("1");
        Assertions.assertThat(client.get("name")).isEqualTo("toto2");
        Assertions.assertThat(client.get("email")).isEqualTo("my.email@toto.com");
        Assertions.assertThat(client.get("dateOfBirth")).isEqualTo("1986-01-17 00:12:12.0");

        created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createProduct {\n" +
                        "  createProduct(name: \"product\", price: 10.99)\n" +
                        "}",
                "data.createProduct");
        Assertions.assertThat(created).isTrue();

        LinkedHashMap product = dgsQueryExecutor.executeAndExtractJsonPath(
                "query readProducts {\n" +
                        "  products{\n" +
                        "    id,\n" +
                        "    name,\n" +
                        "    price\n" +
                        "  }\n" +
                        "}",
                "data.products[0]");

        Assertions.assertThat(product.get("id")).isEqualTo("1");
        Assertions.assertThat(product.get("name")).isEqualTo("product");
        Assertions.assertThat(product.get("price")).isEqualTo(10.99d);

        created = dgsQueryExecutor.executeAndExtractJsonPath(
                "mutation createBooking {\n" +
                        "  createBooking(clientId: 1, productId: 1)\n" +
                        "}",
                "data.createBooking");

        Assertions.assertThat(created).isTrue();

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

        Assertions.assertThat(((LinkedHashMap) booking.get("product")).get("name")).isEqualTo("product");
        Assertions.assertThat(booking.get("bookingDate")).isNotNull();
    }

}
