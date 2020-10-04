import { Moment } from 'moment';
import { Devise } from 'app/shared/model/enumerations/devise.model';

export interface IGestionFidelite {
  id?: number;
  nom?: string;
  points?: number;
  valeur?: number;
  silverMin?: number;
  silverMax?: number;
  goldMin?: number;
  goldMax?: number;
  platiniumMin?: number;
  platiniumMax?: number;
  devise?: Devise;
  etat?: boolean;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
}

export const defaultValue: Readonly<IGestionFidelite> = {
  etat: false,
};
