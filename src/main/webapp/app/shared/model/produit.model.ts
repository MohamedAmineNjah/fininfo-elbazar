import { Moment } from 'moment';
import { IStock } from 'app/shared/model/stock.model';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { Devise } from 'app/shared/model/enumerations/devise.model';
import { SourcePrd } from 'app/shared/model/enumerations/source-prd.model';

export interface IProduit {
  id?: number;
  reference?: string;
  nom?: string;
  codeBarre?: string;
  description?: string;
  etat?: boolean;
  marque?: string;
  nature?: string;
  stockMinimum?: number;
  quantiteVenteMax?: number;
  horsStock?: boolean;
  typeService?: boolean;
  datePremption?: string;
  prixHT?: number;
  tauxTVA?: number;
  prixTTC?: number;
  devise?: Devise;
  sourceProduit?: SourcePrd;
  rating?: string;
  eligibleRemise?: boolean;
  remise?: number;
  debutPromo?: string;
  finPromo?: string;
  imageContentType?: string;
  image?: any;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  enVedette?: boolean;
  imageUrl?: string;
  stocks?: IStock[];
  mouvementStocks?: IMouvementStock[];
  commandeLignes?: ICommandeLignes[];
  categorieNom?: string;
  categorieId?: number;
  sousCategorieNom?: string;
  sousCategorieId?: number;
  uniteCode?: string;
  uniteId?: number;
}

export const defaultValue: Readonly<IProduit> = {
  etat: false,
  horsStock: false,
  typeService: false,
  eligibleRemise: false,
  enVedette: false,
};
