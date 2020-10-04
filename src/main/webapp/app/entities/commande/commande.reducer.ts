import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, IPayload } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommande, defaultValue } from 'app/shared/model/commande.model';
import { calculTotalPrixTTC } from './commande-services';

export const ACTION_TYPES = {
  SEARCH_COMMANDES: 'commande/SEARCH_COMMANDES',
  FETCH_COMMANDE_LIST: 'commande/FETCH_COMMANDE_LIST',
  FETCH_FILTRED_COMMANDE_LIST: 'commande/FETCH_FILTRED_COMMANDE_LIST',
  FETCH_COMMANDE: 'commande/FETCH_COMMANDE',
  CREATE_COMMANDE: 'commande/CREATE_COMMANDE',
  UPDATE_COMMANDE: 'commande/UPDATE_COMMANDE',
  DELETE_COMMANDE: 'commande/DELETE_COMMANDE',
  RESET: 'commande/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommande>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  totaPrixCommandesTTC: 0,
  updateSuccess: false,
};

export type CommandeState = Readonly<typeof initialState>;

// Reducer

export default (state: CommandeState = initialState, action): CommandeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COMMANDES):
    case REQUEST(ACTION_TYPES.FETCH_COMMANDE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILTRED_COMMANDE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMANDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMANDE):
    case REQUEST(ACTION_TYPES.UPDATE_COMMANDE):
    case REQUEST(ACTION_TYPES.DELETE_COMMANDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_COMMANDES):
    case FAILURE(ACTION_TYPES.FETCH_COMMANDE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILTRED_COMMANDE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMANDE):
    case FAILURE(ACTION_TYPES.CREATE_COMMANDE):
    case FAILURE(ACTION_TYPES.UPDATE_COMMANDE):
    case FAILURE(ACTION_TYPES.DELETE_COMMANDE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COMMANDES):
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDE_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_FILTRED_COMMANDE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
        totaPrixCommandesTTC: calculTotalPrixTTC(action.payload.data),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMANDE):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMANDE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMANDE):
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

declare type ICrudGetAllFiltredAction<T> = (
  filter?: string,
  page?: number,
  size?: number,
  sort?: string
) => IPayload<T> | ((dispatch: any) => IPayload<T>);

const apiUrl = 'api/commandes';
const apiSearchUrl = 'api/_search/commandes';

// Actions

export const getSearchEntities: ICrudSearchAction<ICommande> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COMMANDES,
  payload: axios.get<ICommande>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICommande> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMMANDE_LIST,
    payload: axios.get<ICommande>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getFilteredEntities: ICrudGetAllFiltredAction<ICommande> = (filter, page, size, sort) => {
  return {
    type: ACTION_TYPES.FETCH_FILTRED_COMMANDE_LIST,
    payload: axios.get<ICommande>(`${apiUrl}?${filter}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
  };
};

export const getEntity: ICrudGetAction<ICommande> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMANDE,
    payload: axios.get<ICommande>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICommande> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMANDE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommande> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMANDE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommande> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMANDE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
