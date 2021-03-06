import { Moment } from 'moment';
import { IAdresse } from 'app/shared/model/adresse.model';
import { ILivraison } from 'app/shared/model/livraison.model';
import { IAffectationZone } from 'app/shared/model/affectation-zone.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IZone {
  id?: number;
  codeZone?: string;
  nom?: string;
  creeLe?: string;
  creePar?: string;
  modifieLe?: string;
  modifiePar?: string;
  adresses?: IAdresse[];
  livraisons?: ILivraison[];
  affectationZones?: IAffectationZone[];
  commandes?: ICommande[];
}

export const defaultValue: Readonly<IZone> = {};
