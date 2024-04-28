package tn.esprit.espritgather.service;
import tn.esprit.espritgather.entity.Recrutement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.ProcessRecrutement;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;
import tn.esprit.espritgather.repo.ProcessNotFoundException;
import tn.esprit.espritgather.repo.ProcessRecrutementRepository;
import tn.esprit.espritgather.repo.RecrutementRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor

public class ProcessRecrutementIServiceImpl implements IProcessRecrutementService {
    ProcessRecrutementRepository processRecrutementRepository;
    RecrutementRepository recrutementRepository;
    public List<ProcessRecrutement> retrieveAllProcesses() {
        return processRecrutementRepository.findAll();
    }

    public ProcessRecrutement retrieveProcess(Long idProcessRecrutement) {
        return processRecrutementRepository.findById(idProcessRecrutement).get();
    }

    public ProcessRecrutement addProcess(ProcessRecrutement process) {

        return processRecrutementRepository.save(process);

    }

    public void removeProcess(Long idProcessRecrutement) {
        processRecrutementRepository.deleteById(idProcessRecrutement);
    }

    public List<ProcessRecrutement> retrieveProcesssByRecAndUser(Long recId, Long userId) {
        return processRecrutementRepository.findProcessRecrutementByRecrutementIdRecrutementAndUserIdUser(recId,userId);
    }

    public ProcessRecrutement modifyProcess(ProcessRecrutement process) {
        return processRecrutementRepository.save(process);
    }

    public List<ProcessRecrutement> retrieveProcessesByRecrutement(Long idRecrutement) {
        return processRecrutementRepository.findProcessRecrutementByRecrutement_IdRecrutement(idRecrutement);
    }
    @Override
    public boolean compareSkillsAndApprove(Recrutement recrutement, ProcessRecrutement process) {
        Map<Skill, SkillLevel> recruitmentSkills = recrutement.getRequiredSkills();
        Map<Skill, SkillLevel> processSkills = process.getSkills();

        if (recruitmentSkills == null || processSkills == null) {
            process.setApproved(false);
            processRecrutementRepository.save(process);
            return false;
        }

        int totalSkills = recruitmentSkills.size();
        int matchedSkills = 0;

        for (Map.Entry<Skill, SkillLevel> entry : recruitmentSkills.entrySet()) {
            Skill skillName = entry.getKey();
            SkillLevel requiredLevel = entry.getValue();

            if (processSkills.containsKey(skillName)) {
                SkillLevel actualLevel = processSkills.get(skillName);
                if (matchSkillLevel(requiredLevel, actualLevel)) {
                    matchedSkills++;
                }
            }
        }

        // Calculate matching percentage
        double matchPercentage = (double) matchedSkills / totalSkills * 100;

        // Determine approval status based on matching percentage
        if (matchPercentage >= 50){
            process.setApproved(true);
            processRecrutementRepository.save(process);
             return true;
        }

        return false;
    }

    public void approveProcess(Long processId) throws ProcessNotFoundException {
        ProcessRecrutement p = processRecrutementRepository.getReferenceById(processId);
        Recrutement process = recrutementRepository.findById(processId)
                .orElseThrow(() -> new ProcessNotFoundException("Process not found with ID: " + processId));

        if (process.getNiveau() >= 0 && compareSkillsAndApprove(process,p))  {  // Use "vacancies" instead of "niveau"
            process.setNiveau(process.getNiveau() - 1);

            if (process.getNiveau() == 0) {
                recrutementRepository.delete(process); // Delete if vacancies reach zero
                // Optional: Log deletion or notify relevant parties
            } else {
                recrutementRepository.save(process);
            }

        }recrutementRepository.save(process);
    }


    private boolean matchSkillLevel(SkillLevel requiredLevel, SkillLevel actualLevel) {

        return requiredLevel == actualLevel;
    }
    public Long countApprovedProcesses() {
        return processRecrutementRepository.countByApproved(true);
    }

    public Long countNonApprovedProcesses() {
        return processRecrutementRepository.countByApproved(false);
    }

    @Override
    public void insertSkillsForProcess(Long processId, Map<Skill, SkillLevel> skillsToAdd) {

    }


    public Map<Skill, Double> calculateSkillSelectionPercentageIncludingUnapproved() {
        List<ProcessRecrutement> allProcesses = processRecrutementRepository.findAll();
        Map<Skill, Integer> skillCounts = new HashMap<>();


        for (Skill skill : Skill.values()) {
            skillCounts.put(skill, 0);
        }


        for (ProcessRecrutement process : allProcesses) {
            Map<Skill, SkillLevel> processSkills = process.getSkills();
            if (processSkills != null) {
                for (Skill skill : processSkills.keySet()) {
                    skillCounts.put(skill, skillCounts.get(skill) + 1);
                }
            }
        }


        int totalProcesses = allProcesses.size();
        Map<Skill, Double> skillPercentages = new HashMap<>();
        for (Skill skill : skillCounts.keySet()) {
            double percentage = (double) skillCounts.get(skill) / totalProcesses * 100;
            skillPercentages.put(skill, percentage);
        }

        return skillPercentages;
    }


}