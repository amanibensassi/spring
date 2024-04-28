package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.ProcessRecrutement;
import tn.esprit.espritgather.entity.Recrutement;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;
import tn.esprit.espritgather.repo.ProcessNotFoundException;

import java.util.List;
import java.util.Map;

public interface IProcessRecrutementService {
    public List<ProcessRecrutement> retrieveAllProcesses();
    public ProcessRecrutement retrieveProcess(Long idProcessRecrutement);
    public ProcessRecrutement addProcess(ProcessRecrutement process);
    public void removeProcess(Long idProcessRecrutement);
    public ProcessRecrutement modifyProcess(ProcessRecrutement process);
    List<ProcessRecrutement> retrieveProcessesByRecrutement(Long idRecrutement);
    public boolean compareSkillsAndApprove(Recrutement recrutement, ProcessRecrutement process);
    public void approveProcess(Long idProcessRecrutement) throws ProcessNotFoundException;
    //void createProcessRecrutement(ProcessRecrutement processRecrutement);
    public Long countApprovedProcesses();
    public Long countNonApprovedProcesses();
    public void insertSkillsForProcess(Long processId, Map<Skill, SkillLevel> skillsToAdd) ;

    public Map<Skill, Double> calculateSkillSelectionPercentageIncludingUnapproved();

    List<ProcessRecrutement> retrieveProcesssByRecAndUser(Long recId, Long userId);


}

