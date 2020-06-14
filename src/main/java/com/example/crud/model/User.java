package com.example.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
    private Set<Document> documents = new HashSet<>();

    public User(String firstName) {
        this.firstName = firstName;
    }

    protected Set<Document> getDocumentsInternal() {
        if (this.documents == null) {
            this.documents = new HashSet<>();
        }
        return this.documents;
    }

    protected void setDocumentsInternal(Set<Document> documents) {
        this.documents = documents;
    }

    public List<Document> getDocuments() {
        List<Document> sortedDocuments = new ArrayList<>(getDocumentsInternal());
        PropertyComparator.sort(sortedDocuments, new MutableSortDefinition("expiryDate", true, true));
        return Collections.unmodifiableList(sortedDocuments);
    }

    public void addDocument(Document document) {
        if(document.isNew()){
            getDocumentsInternal().add(document);
        }
        document.setOwner(this);
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documents=" + documents +
                '}';
    }
}
