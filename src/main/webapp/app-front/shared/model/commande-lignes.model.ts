import { Moment } from 'moment';

export interface ICommandeLignes {
  id?: number;
  quantite?: number;
  prixHT?: number;
  remise?: number;
  tva?: number;
  prixTTC?: number;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  refCommandeReference?: string;
  refCommandeId?: number;
  refProduitReference?: string;
  refProduitId?: number;
}

export const defaultValue: Readonly<ICommandeLignes> = {};
