# Spring Boot Crud with Postgres & DGS GraphQL

This project is aimed to quickly test the stack :
- Spring boot (https://spring.io/projects/spring-boot)
- Netflix DGS (https://netflix.github.io/dgs/)
- Spring Data Postgres (https://spring.io/projects/spring-data)
- Database initialisation with Flyway (https://flywaydb.org/documentation/usage/plugins/springboot)
- Spring Security Basic Auth (https://spring.io/projects/spring-security)

## Clone & build

- Tests are executed on an h2 database 
- Simply `mvn clean install` it

## Run it

You can run the app with a local postgres database by 
- running `docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres`
- Launch the `SpringBootCrudApplication.class`
- Two users are available with Basic Auth : 
    - standardUser (read only)
    - adminUser (create & read)

## GraphQl queries
```graphql
# Sample Queries available for users : standardUser, adminUser : 
query readClients {
  clients{
    id,
    name,
    email,
    dateOfBirth
  }
}

query readProducts {
  products{
    name,
    price
  }
}

query readBookings {
  bookings{
    product {
      name
    },
    client {
      name
    },
    bookingDate
  }
}

query readBookingsByClient {
  bookingsByClient (clientId: 1){
    product {
      name
    }
    bookingDate
  }
}

# Sample Mutations available for users : adminUser : 
mutation createClient {
    createClient(name: "toto3", email: "my.email@toto.com", dateOfBirth: "1986-01-17 00:12:12.000")
}

mutation createProduct {
    createProduct(name: "produit2", price: 10.45)
}

mutation createBooking {
    createBooking(productId: 2, clientId: 2)
}
```

Same app with other stacks :
- https://github.com/fabienmifsud/quarkus-crud
- https://github.com/fabienmifsud/micronaut-crud