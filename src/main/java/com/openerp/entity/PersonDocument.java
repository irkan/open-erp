package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hr_person_document")
@Getter
@Setter
@NoArgsConstructor
public class PersonDocument {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_person_id", nullable = false)
    private Person person;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_document_type_id")
    private Dictionary documentType;

    @Lob
    @Column(name = "document", length=300000,  columnDefinition="BLOB")
    private byte[] bytes;

    @Column(name = "extension")
    private String extension;
}
