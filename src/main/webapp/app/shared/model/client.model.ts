import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';
import { ICommande } from 'app/shared/model/commande.model';
import { Civilite } from 'app/shared/model/enumerations/civilite.model';
import { RegMod } from 'app/shared/model/enumerations/reg-mod.model';
import { ProfileClient } from 'app/shared/model/enumerations/profile-client.model';

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
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IClient> = {
  etat: false,
};
