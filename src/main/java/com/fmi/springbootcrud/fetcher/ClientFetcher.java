package com.fmi.springbootcrud.fetcher;

import com.fmi.springbootcrud.data.Client;
import com.fmi.springbootcrud.repository.ClientRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.text.ParseException;

@DgsComponent
public class ClientFetcher {

    @Autowired
    private ClientRepository clientRepository;

    @DgsData(parentType = "Query", field = "clients")
    @RolesAllowed({"ADMIN", "USER"})
    public Iterable<Client> getAll() {
        return this.clientRepository.findAll();
    }

    @DgsData(parentType = "Mutation", field = "createClient")
    @RolesAllowed("ADMIN")
    public boolean createClient(@InputArgument(value = "name") String name,
                                @InputArgument(value = "email") String email,
                                @InputArgument(value = "dateOfBirth") String dateOfBirth) throws ParseException {

        Client client = new Client();
        client.setEmail(email);
        client.setName(name);
        if(dateOfBirth != null) {
            client.setDateOfBirth(DateUtils.parseDate(dateOfBirth, "yyyy-MM-dd HH:mm:ss.SSS"));
        }

        return this.clientRepository.save(client) != null;
    }

}
