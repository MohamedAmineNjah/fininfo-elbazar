import React from 'react';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const generateNextSatus = (commande) => {
    switch (commande['statut']) {
        case "Reservee": return "Commandee";
        case "Commandee": return "Livraison";
        case "Livraison": return "Livree";
        case "Livree": return "Annulee";
        default:
            break;
    }
}

const generateUpdateCommande = (commande, statut) => {

    const newCommande = commande;

    newCommande['statut'] = statut;

}


const CommandeStatus = ({ commande, onUpdateStatus }) => {
    const nextStatus = generateNextSatus(commande);
    const newCommande = generateUpdateCommande(commande, nextStatus);
    return (
        <Button color="warning" onClick={() => onUpdateStatus(newCommande)}>
            <FontAwesomeIcon icon="plus" />{' '}
        </Button>
    );
}

export default CommandeStatus;




