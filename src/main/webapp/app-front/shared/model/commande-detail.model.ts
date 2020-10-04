import { ICommandeLignesHeader } from './commande-lignes-header.model';

export interface ICommandeDetail {
  commande_lignes?: ICommandeLignesHeader[];
}

export const defaultValue: Readonly<ICommandeDetail> = {};
