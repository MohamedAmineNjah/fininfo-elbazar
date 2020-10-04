import { Moment } from 'moment';
import { ICommande } from 'app/shared/model/commande.model';

export interface IAdresse {
  id?: number;
  principale?: boolean;
  prenom?: string;
  nom?: string;
  adresse?: string;
  gouvernorat?: string;
  ville?: string;
  localite?: string;
  indication?: string;
  telephone?: number;
  mobile?: number;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  commandes?: ICommande[];
  clientId?: number;
  zoneNom?: string;
  zoneId?: number;
  codePostalCodePostal?: string;
  codePostalId?: number;
}

export const defaultValue: Readonly<IAdresse> = {
  principale: false,
};
