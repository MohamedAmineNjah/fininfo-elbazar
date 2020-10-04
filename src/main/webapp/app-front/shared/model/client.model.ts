import { Moment } from 'moment';
import { IAdresse } from '../../shared/model/adresse.model';
import { ICommande } from '../../shared/model/commande.model';
import { Civilite } from '../../shared/model/enumerations/civilite.model';
import { RegMod } from '../../shared/model/enumerations/reg-mod.model';
import { ProfileClient } from '../../shared/model/enumerations/profile-client.model';

export interface IClient {
  id?: number;
  civilite?: Civilite;
  prenom?: string;
  nom?: string;
  dateDeNaissance?: string;
  email?: string;
  mobile?: number;
  reglement?: RegMod;
  etat?: boolean;
  inscription?: string;
  derniereVisite?: string;
  totalAchat?: number;
  profile?: ProfileClient;
  pointsFidelite?: number;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  adresses?: IAdresse[];
  commandes?: ICommande[];
}

export const defaultValue: Readonly<IClient> = {
  etat: false,
};
