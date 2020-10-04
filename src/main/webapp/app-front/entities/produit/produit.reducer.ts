import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProduit, defaultValue } from 'app/shared/model/produit.model';

export const ACTION_TYPES = {
  SEARCH_PRODUITS: 'produit/SEARCH_PRODUITS',
  FETCH_PRODUIT_LIST: 'produit/FETCH_PRODUIT_LIST',
  FETCH_PRODUIT: 'produit/FETCH_PRODUIT',
  CREATE_PRODUIT: 'produit/CREATE_PRODUIT',
  UPDATE_PRODUIT: 'produit/UPDATE_PRODUIT',
  DELETE_PRODUIT: 'produit/DELETE_PRODUIT',
  SET_BLOB: 'produit/SET_BLOB',
  RESET: 'produit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProduit>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProduitState = Readonly<typeof initialState>;

// Reducer

export default (state: ProduitState = initialState, action): ProduitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRODUITS):
    case REQUEST(ACTION_TYPES.FETCH_PRODUIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUIT):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUIT):
    case REQUEST(ACTION_TYPES.DELETE_PRODUIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PRODUITS):
    case FAILURE(ACTION_TYPES.FETCH_PRODUIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUIT):
    case FAILURE(ACTION_TYPES.CREATE_PRODUIT):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUIT):
    case FAILURE(ACTION_TYPES.DELETE_PRODUIT):
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
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUIT):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
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

// Actions

export const getSearchEntities: ICrudSearchAction<IProduit> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PRODUITS,
  payload: axios.get<IProduit>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IProduit> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUIT_LIST,
    payload: axios.get<IProduit>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProduit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUIT,
    payload: axios.get<IProduit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProduit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProduit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUIT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProduit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUIT,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
