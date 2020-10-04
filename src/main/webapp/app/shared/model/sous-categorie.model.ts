import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';
import { IStock } from 'app/shared/model/stock.model';

export interface ISousCategorie {
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
  produits?: IProduit[];
  stocks?: IStock[];
  categorieNom?: string;
  categorieId?: number;
}

export const defaultValue: Readonly<ISousCategorie> = {
  etat: false,
};
