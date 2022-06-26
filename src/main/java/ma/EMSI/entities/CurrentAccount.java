package ma.EMSI.entities;

import lombok.*;
import javax.persistence.*;

@Entity // c'est une entite jpa
@DiscriminatorValue("CA") // dans la colonnes type la valeur affecté est CA
@Data @NoArgsConstructor @AllArgsConstructor
public class CurrentAccount extends BankAccount {
    private double overDraft;}
