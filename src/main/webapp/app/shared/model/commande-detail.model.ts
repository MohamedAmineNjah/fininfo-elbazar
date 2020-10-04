import { ICommandeHeader } from './commande-header.model';
import { ICommandeLignesHeader } from './commande-lignes-header.model';
import { ICommande } from './commande.model';

export interface ICommandeDetail {
  commandeDTO?: ICommande;
  commandeLignesDTO?: ICommandeLignesHeader[];
}

export const defaultValue: Readonly<ICommandeDetail> = {};
