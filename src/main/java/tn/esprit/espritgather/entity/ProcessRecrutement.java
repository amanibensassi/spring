package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProcessRecrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProcessRecrutement;

    private Date interviewDate;
    private String whyToJoin;
    private String availability;
    private boolean integratedInOtherClubs;
    private List<String> otherClubs;

    @Getter
    @Setter
    private boolean approved;
    @ElementCollection
    @MapKeyJoinColumn(name = "skillName")
    @Column(name = "level")

    private Map<Skill, SkillLevel> skills;


    @ManyToOne
    @JoinColumn(name = "recrutement")
    private Recrutement recrutement;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;




    public Map<Skill, SkillLevel> getSkills() {
        if (skills != null) {
            return Collections.unmodifiableMap(skills);
        } else {
            return Collections.emptyMap();
        }
    }

    public void approveProcess() {
        if (this.getRecrutement() != null && this.getRecrutement().getNiveau() > 0) {
            this.getRecrutement().setNiveau(this.getRecrutement().getNiveau() - 1);
            // ... logic to potentially delete recruitment (consider transactions)
        }
    }
    public boolean getApproved() {
        return approved;
    }

}


