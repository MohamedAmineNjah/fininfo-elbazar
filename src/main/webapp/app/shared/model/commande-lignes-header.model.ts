import { Moment } from 'moment';

export interface ICommandeLignesHeader {
  id?: number;
  quantite?: number;
  prixHT?: number;
  remise?: number;
  tva?: number;
  prixTTC?: number;
  refCommandeId?: number;
  refProduitId?: number;
  refProduitReference?: string;
  nomProduit?: string;   
}

export const defaultValue: Readonly<ICommandeLignesHeader> = {};