package ma.EMSI.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.EMSI.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // specifier la strategier=> joined, single table, table per classe
// on doit preciser la colonne type dans les classes filles
@DiscriminatorColumn(name="TYPE", length =4)
public abstract class BankAccount {// pas besoin de creer un objet bankaccount psuiqu'elle est abstraite

    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperationList;
}
