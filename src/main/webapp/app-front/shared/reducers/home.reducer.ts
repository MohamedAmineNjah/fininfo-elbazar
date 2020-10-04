import axios from 'axios';

import { SUCCESS } from './action-type.util';
import { IProduit } from '../model/produit.model';
import { IListPromo, defaultValue } from '../model/list-promo.model';

export const ACTION_TYPES = {
  GET_PROMOPROD: 'home/GET_PROMOPROD',
  GET_NEWPROD: 'home/GET_NEWPROD',
  GET_FAVPROD: 'home/GET_FAVPROD',
  GET_LISTSLIDE: 'home/GET_LISTSLIDE',
  GET_LISTPARTENAIRE: 'home/GET_LISTPARTENAIRE',
  GET_INFOSOCIETE: 'footer/GET_INFOSOCIETE',
};

const initialState = {
  listpromoprod: defaultValue,
  listnewprod: defaultValue,
  listfavprod: defaultValue,
  listSliders: [],
  listPartenaire: [],
  listInfosociete: [],
  valeurMinPanier: 40,
};

export type ProduitListState = Readonly<typeof initialState>;

export default (state: ProduitListState = initialState, action): ProduitListState => {
  switch (action.type) {
    case SUCCESS(ACTION_TYPES.GET_PROMOPROD): {
      return {
        ...state,
        listpromoprod: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_NEWPROD): {
      return {
        ...state,
        listnewprod: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_LISTSLIDE): {
      return {
        ...state,
        listSliders: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_LISTPARTENAIRE): {
      return {
        ...state,
        listPartenaire: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_FAVPROD): {
      return {
        ...state,
        listfavprod: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_INFOSOCIETE): {
      return {
        ...state,
        listInfosociete: action.payload.data,
        valeurMinPanier: action.payload.data[0].valeurMinPanier,
      };
    }
    default:
      return state;
  }
};

export const getPromoProd = () => ({
  type: ACTION_TYPES.GET_PROMOPROD,
  payload: axios.get('api/produits/promo'),
});

export const getNewProd = () => ({
  type: ACTION_TYPES.GET_NEWPROD,
  payload: axios.get('api/produits/newprod'),
});
export const getFavProd = () => ({
  type: ACTION_TYPES.GET_FAVPROD,
  payload: axios.get('api/produits/vedette'),
});
export const getListSlide = () => ({
  type: ACTION_TYPES.GET_LISTSLIDE,
  payload: axios.get('api/slides/carousel'),
});
export const getPartenaire = () => ({
  type: ACTION_TYPES.GET_LISTPARTENAIRE,
  payload: axios.get('api/slides/partners'),
});
export const getinfosociete = () => ({
  type: ACTION_TYPES.GET_INFOSOCIETE,
  payload: axios.get('api/info-societes'),
});
