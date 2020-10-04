import { Moment } from 'moment';
import { ProfileClient } from './enumerations/profile-client.model';

export interface ILivraison2 {
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

export const defaultValue: Readonly<ILivraison2> = {};
