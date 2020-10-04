import { Moment } from 'moment';

export interface ICommandeLignesHeader {
  id?: number;
  quantite?: number;
  prix_ht?: number;
  remise?: number;
  tva?: number;
  prix_ttc?: number;
  refCommandeId?: number;
  reference?: string;
  ref_produit_id?: number;
  nom?: string;
}

export const defaultValue: Readonly<ICommandeLignesHeader> = {};
