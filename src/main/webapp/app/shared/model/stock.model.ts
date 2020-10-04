import { Moment } from 'moment';

export interface IStock {
  id?: number;
  stockReserve?: number;
  stockCommande?: number;
  stockPhysique?: number;
  stockDisponible?: number;
  stockMinimum?: number;
  derniereEntre?: string;
  derniereSortie?: string;
  alerteStock?: boolean;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  refProduitReference?: string;
  refProduitId?: number;
  idCategorieNom?: string;
  idCategorieId?: number;
  idSousCategorieNom?: string;
  nomProduit?: string;
  idSousCategorieId?: number;
}

export const defaultValue: Readonly<IStock> = {
  alerteStock: false,
};
