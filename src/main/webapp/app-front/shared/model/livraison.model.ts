import { Moment } from 'moment';
import { ProfileClient } from '../../shared/model/enumerations/profile-client.model';

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
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<ILivraison> = {};
