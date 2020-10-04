import { Moment } from 'moment';
import { TypeMvt } from '../../shared/model/enumerations/type-mvt.model';

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
  refProduitReference?: string;
  refProduitId?: number;
  refCommandeReference?: string;
  refCommandeId?: number;
}

export const defaultValue: Readonly<IMouvementStock> = {};
