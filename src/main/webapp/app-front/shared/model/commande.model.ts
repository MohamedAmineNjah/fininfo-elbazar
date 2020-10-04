import { Moment } from 'moment';
import { IMouvementStock } from '../../shared/model/mouvement-stock.model';
import { ICommandeLignes } from '../../shared/model/commande-lignes.model';
import { StatCmd } from '../../shared/model/enumerations/stat-cmd.model';
import { Origine } from '../../shared/model/enumerations/origine.model';
import { Devise } from '../../shared/model/enumerations/devise.model';
import { RegMod } from '../../shared/model/enumerations/reg-mod.model';

export interface ICommande {
  id?: number;
  reference?: string;
  date?: string;
  statut?: StatCmd;
  origine?: Origine;
  totalHT?: number;
  totalTVA?: number;
  totalRemise?: number;
  totTTC?: number;
  devise?: Devise;
  pointsFidelite?: number;
  reglement?: RegMod;
  dateLivraison?: string;
  dateCreation?: string;
  dateAnnulation?: string;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  mouvementStocks?: IMouvementStock[];
  commandeLignes?: ICommandeLignes[];
  idClientId?: number;
  idAdresseId?: number;
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<ICommande> = {};
