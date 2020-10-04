import { Moment } from 'moment';
import { TypeMvt } from 'app/shared/model/enumerations/type-mvt.model';
import { StatCmd } from './enumerations/stat-cmd.model';

export interface IMouvementStock {
  id?: number;
  type?: TypeMvt;
  date?: string;
  sens?: number;
  quantite?: number;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  reference?: string;
  refProduitReference?: string;
  refProduitId?: number;
  refCommandeReference?: string;
  refCommandeId?: number;
  nomProduit?: string;
  refCommandeStatut?: StatCmd;
}

export const defaultValue: Readonly<IMouvementStock> = {};
