import axios from 'axios';
import {
  ICrudSetSubCat,
  ICrudGetAction,
  ICrudGetAllProdAction,
  ICrudSearchProdAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from '../../shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from '../../shared/reducers/action-type.util';

import { IProduit, defaultValue } from '../../shared/model/produit.model';

export const ACTION_TYPES = {
  SEARCH_PRODUITS: 'produit/SEARCH_PRODUITS',
  FETCH_PRODUIT_LIST: 'produit/FETCH_PRODUIT_LIST',
  FETCH_PRODUIT: 'produit/FETCH_PRODUIT',
  SET_SUB_CATEGORY: 'produit/SET_SUB_CATEGORY',
  FETCH_PRODUIT_LISTSEARCH: 'produit/FETCH_PRODUIT_LISTSEARCH',
  RESET: 'produits/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProduit>,
  entity: {} as any,
  idSubCategory: null,
  nameSubCategory: '',
  updating: false,
  totalPages: 0,
  updateSuccess: false,
};

export type ProduitState = Readonly<typeof initialState>;

// Reducer

export default (state: ProduitState = initialState, action): ProduitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRODUITS):
    case REQUEST(ACTION_TYPES.FETCH_PRODUIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUIT):
    case REQUEST(ACTION_TYPES.FETCH_PRODUIT_LISTSEARCH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };

    case FAILURE(ACTION_TYPES.SEARCH_PRODUITS):
    case FAILURE(ACTION_TYPES.FETCH_PRODUIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUIT):
    case FAILURE(ACTION_TYPES.FETCH_PRODUIT_LISTSEARCH):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PRODUITS):
    case SUCCESS(ACTION_TYPES.FETCH_PRODUIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data.content,
        idSubCategory: action.idSubCat,
        totalPages: parseInt(action.payload.data.totalPages, 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUIT_LISTSEARCH):
      return {
        ...state,
        loading: false,
        entities: action.payload.data.content,
        totalPages: parseInt(action.payload.data.totalPages, 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case ACTION_TYPES.SET_SUB_CATEGORY:
      return {
        ...state,
        nameSubCategory: action.payload,
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/produits';
const apiSearchUrl = 'api/_search/produits';
const apiProductUrl = 'api/produits/souscategorie/';
const apiUrl2 = 'api/search/produitnom';

// Actions

export const getSearchEntities: ICrudSearchProdAction<IProduit> = (idSubCategory, query, page, size, sort) => {
  const requestUrl = `${apiProductUrl}${idSubCategory}/${query}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.SEARCH_PRODUITS,
    payload: axios.get<IProduit>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
    idSubCat: idSubCategory,
  };
};
export const getEntities: ICrudGetAllProdAction<IProduit> = (idSubCategory, page, size, sort) => {
  const requestUrl = `${apiProductUrl}${idSubCategory}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUIT_LIST,
    payload: axios.get<IProduit>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
    idSubCat: idSubCategory,
  };
};

export const getEntity: ICrudGetAction<IProduit> = id => {
  const requestUrl = `api/produit_/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUIT,
    payload: axios.get(requestUrl),
  };
};
export const setNameSubCat = name => {
  return {
    type: ACTION_TYPES.SET_SUB_CATEGORY,
    payload: name,
  };
};

export const getEntitieshome: ICrudGetAllProdAction<IProduit> = (nom, page, size, sort) => {
  const requestUrl = `${apiUrl2}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUIT_LISTSEARCH,
    payload: axios.post<IProduit>(`${requestUrl}`, { name: nom }),
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
