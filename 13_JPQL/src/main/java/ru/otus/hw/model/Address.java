package ru.otus.hw.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "address", schema = "public", catalog = "postgres")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "client")
@NamedEntityGraph(name = "address_client", attributeNodes = {
        @NamedAttributeNode("client")
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    @OneToOne(mappedBy = "address")
    private Client client;

    public Address(String street) {
        this.street = street;
    }
}
