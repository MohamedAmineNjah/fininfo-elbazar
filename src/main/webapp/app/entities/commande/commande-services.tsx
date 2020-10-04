import { ICommande } from 'app/shared/model/commande.model';

const generateUpdateCommande = (commande, statut) => {
  const newCommande = commande;

  newCommande['statut'] = statut;

  return newCommande;
};

export const updateBeforeStatus = (commande, updateFunction) => {
  if (commande['statut'] !== 'Livraison') return;
  const beforeCommande: ICommande = generateUpdateCommande(commande, 'Commandee');
  updateFunction(beforeCommande);
};

export const generateNextSatus = commande => {
  switch (commande['statut']) {
    case 'Reservee':
      return 'Commandee';
    case 'Commandee':
      return 'Livraison';
    case 'Livraison':
      return 'Livree';
    default:
      return commande['statut'];
  }
};

export const generateNextSatusLabel = commande => {
  switch (commande['statut']) {
    case 'Reservee':
      return 'Confirmer';
    case 'Commandee':
      return 'En cours de livraison';
    case 'Livraison':
      return 'Livrer';
    default:
      return 'Aucune Action';
  }
};

export const updateNextStatus = (commande, updateFunction) => {
  const status = generateNextSatus(commande);
  if (status === commande['statut']) return;
  const nextCommande: ICommande = generateUpdateCommande(commande, status);
  updateFunction(nextCommande);
};

export const updateCancelStatus = (commande, updateFunction) => {
  if (commande['statut'] === 'Livree') return;
  const cancelCommande: ICommande = generateUpdateCommande(commande, 'Annulee');
  const today = new Date().toISOString().substring(0, 10);
  cancelCommande['dateAnnulation'] = today;
  updateFunction(cancelCommande);
};

export const calculTotalPrixTTC = (listCommandes) => {
  let totalPrixTTC = 0;


  listCommandes.map((commande, i) => {
    totalPrixTTC = totalPrixTTC + commande.totTTC;
  });

 return totalPrixTTC;
};

