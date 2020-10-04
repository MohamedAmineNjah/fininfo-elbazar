import { Moment } from 'moment';
import { IAdresse } from '../../shared/model/adresse.model';

export interface IAffectationZone {
  id?: number;
  gouvernorat?: string;
  ville?: string;
  localite?: string;
  codePostal?: number;
  modifieLe?: string;
  modifiePar?: string;
  adresses?: IAdresse[];
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<IAffectationZone> = {};
