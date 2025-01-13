package ru.otus.hw.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "client", schema = "public", catalog = "postgres")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"address", "phones"})
@NamedEntityGraph(name = "client_address_phone", attributeNodes = {
        @NamedAttributeNode("address"),
        @NamedAttributeNode("phones")
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JoinColumn(name = "customer_id")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Phone> phones;
}
