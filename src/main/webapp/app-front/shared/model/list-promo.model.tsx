import { IProduit } from './produit.model';

export interface IListPromo {
  content?: IProduit[];
}

export const defaultValue: Readonly<IListPromo> = {};
