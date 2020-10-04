import axios from 'axios';
import { SUCCESS, REQUEST } from '../../shared/reducers/action-type.util';

import { ICommande } from '../../shared/model/commande.model';
export const ACTION_TYPES = {
  EDIT_PANIER: 'panier/EDIT_PANIER',
  EDIT_PRIX_TOTAL: 'panier/EDIT_PRIX_TOTAL',
  GET_ADRESSE: 'panier/GET_ADRESSE',
  GET_LIVRAISON_PARAM: 'panier/GET_LIVRAISON_PARAM',
  SEND_COMMANDE: 'panier/SEND_COMMANDE',
  CLEAR_LIST: 'panier/CLEAR_LIST',
};

const jsonarr = JSON.parse(localStorage.getItem('panier'));
const initialState = {
  listProduitsPanier: jsonarr ? jsonarr.listItemPanier : [],
  prixTotal: 0,
  adresses: [],
  listProduitsSended: [],
  livraisonParm: {},
  commandeRep: {},
  isSended: false,
};

export type PanierState = Readonly<typeof initialState>;

export default (state: PanierState = initialState, action): PanierState => {
  switch (action.type) {
    case ACTION_TYPES.EDIT_PANIER: {
      return {
        ...state,
        listProduitsPanier: action.payload,
      };
    }
    case ACTION_TYPES.EDIT_PRIX_TOTAL: {
      return {
        ...state,
        prixTotal: action.payload,
      };
    }
    case ACTION_TYPES.CLEAR_LIST: {
      return {
        ...state,
        prixTotal: 0,
        listProduitsPanier: [],
        listProduitsSended: action.payload,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_ADRESSE): {
      return {
        ...state,
        adresses: action.payload.data,
      };
    }
    case SUCCESS(ACTION_TYPES.GET_LIVRAISON_PARAM): {
      return {
        ...state,
        livraisonParm: action.payload.data,
      };
    }
    case REQUEST(ACTION_TYPES.SEND_COMMANDE): {
      return {
        ...state,
        commandeRep: [],
      };
    }
    case SUCCESS(ACTION_TYPES.SEND_COMMANDE): {
      return {
        ...state,
        commandeRep: action.payload.data,
        isSended: true,
      };
    }
    default:
      return state;
  }
};
export const editItemPanier = listItemPanier => {
  const arrJson = JSON.stringify({ listItemPanier });
  localStorage.setItem('panier', arrJson);
  return {
    type: ACTION_TYPES.EDIT_PANIER,
    payload: listItemPanier,
  };
};
export const editPrixTotal = prixTotaL => {
  return {
    type: ACTION_TYPES.EDIT_PRIX_TOTAL,
    payload: prixTotaL,
  };
};
export const getAdressePrincipal = idClient => {
  return {
    type: ACTION_TYPES.GET_ADRESSE,
    payload: axios.get('api/adresses/client'),
  };
};
export const getLivraisonParam = infoClient => {
  return {
    type: ACTION_TYPES.GET_LIVRAISON_PARAM,
    payload: axios.get('api/livraisons/formule/' + infoClient.val + '/' + infoClient.ville),
  };
};

export const setSendedProduit = listeCommande => {
  return {
    type: ACTION_TYPES.CLEAR_LIST,
    payload: listeCommande,
  };
};
export const sendCommande = (data, listeCommande) => async dispatch => {
  localStorage.clear();

  const result = await dispatch({
    type: ACTION_TYPES.SEND_COMMANDE,
    payload: axios.post('api/getcmds', data),
  });
  dispatch(setSendedProduit(listeCommande));
  return result;
};
