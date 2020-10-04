import { Moment } from 'moment';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { StatCmd } from 'app/shared/model/enumerations/stat-cmd.model';
import { Origine } from 'app/shared/model/enumerations/origine.model';
import { Devise } from 'app/shared/model/enumerations/devise.model';
import { RegMod } from 'app/shared/model/enumerations/reg-mod.model';
import { IClient } from './client.model';

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
  prenom?: string;
  nom?: string;
  adresse?: string;
  gouvernorat?: string;
  ville?: string;
  localite?: string;
  codePostal?: number;
  indication?: string;
  telephone?: number;
  mobile?: number;
  fraisLivraison?: number;
  mouvementStocks?: IMouvementStock[];
  commandeLignes?: ICommandeLignes[];
  idClientId?: number;
  nomClient?: string;
  prenomClient?: string;
  idAdresseId?: number;
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<ICommande> = {};

export const defaultClientValue: Readonly<IClient> = {};
