package ru.otus.hw.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "phone", schema = "public", catalog = "postgres")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "client")
@NamedEntityGraph(name = "phone_client", attributeNodes = {
        @NamedAttributeNode("client")
})
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(String number) {
        this.number = number;
    }
}
