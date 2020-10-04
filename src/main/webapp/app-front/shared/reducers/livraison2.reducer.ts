import { REQUEST, FAILURE } from './action-type.util';
import { ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { ILivraison2 } from './../model/livraison2.model';
import axios from 'axios';
import { SUCCESS } from './action-type.util';
import { IProduit } from '../model/produit.model';
import { IListPromo, defaultValue } from '../model/list-promo.model';

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILivraison2>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type livraison2State = Readonly<typeof initialState>;
export const ACTION_TYPES = {
  SEARCH_LIVRAISONS: 'livraison/SEARCH_LIVRAISONS',
  FETCH_LIVRAISON_LIST: 'livraison/FETCH_LIVRAISON_LIST',
  FETCH_LIVRAISON: 'livraison/FETCH_LIVRAISON',
  CREATE_LIVRAISON: 'livraison/CREATE_LIVRAISON',
  UPDATE_LIVRAISON: 'livraison/UPDATE_LIVRAISON',
  DELETE_LIVRAISON: 'livraison/DELETE_LIVRAISON',
  RESET: 'livraison/RESET',
};
export default (state: livraison2State = initialState, action): livraison2State => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_LIVRAISONS):
    case REQUEST(ACTION_TYPES.FETCH_LIVRAISON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LIVRAISON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LIVRAISON):
    case REQUEST(ACTION_TYPES.UPDATE_LIVRAISON):
    case REQUEST(ACTION_TYPES.DELETE_LIVRAISON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_LIVRAISONS):
    case FAILURE(ACTION_TYPES.FETCH_LIVRAISON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LIVRAISON):
    case FAILURE(ACTION_TYPES.CREATE_LIVRAISON):
    case FAILURE(ACTION_TYPES.UPDATE_LIVRAISON):
    case FAILURE(ACTION_TYPES.DELETE_LIVRAISON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_LIVRAISONS):
    case SUCCESS(ACTION_TYPES.FETCH_LIVRAISON_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LIVRAISON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LIVRAISON):
    case SUCCESS(ACTION_TYPES.UPDATE_LIVRAISON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LIVRAISON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/livraisons';
const apiSearchUrl = 'api/_search/livraisons';

// Actions

export const getSearchEntities: ICrudSearchAction<ILivraison2> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_LIVRAISONS,
  payload: axios.get<ILivraison2>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ILivraison2> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LIVRAISON_LIST,
  payload: axios.get<ILivraison2>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});
export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
