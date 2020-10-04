import { Moment } from 'moment';

export interface IInfoSociete {
  id?: number;
  nomSociete?: string;
  adresse?: string;
  tel1?: number;
  tel2?: number;
  tel3?: number;
  email1?: string;
  email2?: string;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
}

export const defaultValue: Readonly<IInfoSociete> = {};
