import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';

export interface IAffectationZone {
  id?: number;
  gouvernorat?: string;
  ville?: string;
  localite?: string;
  codePostal?: number;
  modifieLe?: string;
  modifiePar?: string;
  idVille?: number;
  adresses?: IAdresse[];
  zoneNom?: string;
  zoneId?: number;
}

export const defaultValue: Readonly<IAffectationZone> = {};
