import { Moment } from 'moment';
import { ProfileClient } from 'app/shared/model/enumerations/profile-client.model';

export interface ILivraison {
  id?: number;
  categorieClient?: ProfileClient;
  intervalValeur?: number;
  frais?: number;
  date?: number;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  valeurMin?: number;
  valeurMax?: number;
  dateLivraison?: string;
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<ILivraison> = {};
