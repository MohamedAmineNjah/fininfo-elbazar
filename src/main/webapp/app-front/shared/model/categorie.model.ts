import { Moment } from 'moment';
import { ISousCategorie } from '../../shared/model/sous-categorie.model';
import { IProduit } from '../../shared/model/produit.model';
import { IStock } from '../../shared/model/stock.model';

export interface ICategorie {
  id?: number;
  nom?: string;
  description?: string;
  position?: number;
  etat?: boolean;
  imageContentType?: string;
  image?: any;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  sousCategories?: ISousCategorie[];
  produits?: IProduit[];
  stocks?: IStock[];
}

export const defaultValue: Readonly<ICategorie> = {
  etat: false,
};
