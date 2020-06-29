package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "action_reason")
@Getter
@Setter
@NoArgsConstructor
public class ActionReason {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "action_reason_seq")
    @SequenceGenerator(sequenceName = "action_reason_seq", allocationSize = 1, name = "action_reason_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_id")
    private Action action;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_reason_id")
    private Dictionary reason;
}
