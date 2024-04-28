package tn.esprit.espritgather.service;


import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;

import java.util.List;

public interface IRecrutementService {
    public List<Recrutement> retrieveAllRecrutements();
    public Recrutement retrieveRecrutement(Long idRecrutement);
    public Recrutement addRecrutement(Recrutement recrutement);
    public void removeRecrutement(Long idRecrutement);
    public Recrutement modifyRecrutement(Recrutement recrutement);

    List<Recrutement> retrieverecsByUser(Long userId);


}
