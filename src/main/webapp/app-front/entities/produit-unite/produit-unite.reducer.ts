import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProduitUnite, defaultValue } from 'app/shared/model/produit-unite.model';

export const ACTION_TYPES = {
  SEARCH_PRODUITUNITES: 'produitUnite/SEARCH_PRODUITUNITES',
  FETCH_PRODUITUNITE_LIST: 'produitUnite/FETCH_PRODUITUNITE_LIST',
  FETCH_PRODUITUNITE: 'produitUnite/FETCH_PRODUITUNITE',
  CREATE_PRODUITUNITE: 'produitUnite/CREATE_PRODUITUNITE',
  UPDATE_PRODUITUNITE: 'produitUnite/UPDATE_PRODUITUNITE',
  DELETE_PRODUITUNITE: 'produitUnite/DELETE_PRODUITUNITE',
  RESET: 'produitUnite/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProduitUnite>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProduitUniteState = Readonly<typeof initialState>;

// Reducer

export default (state: ProduitUniteState = initialState, action): ProduitUniteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRODUITUNITES):
    case REQUEST(ACTION_TYPES.FETCH_PRODUITUNITE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUITUNITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUITUNITE):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUITUNITE):
    case REQUEST(ACTION_TYPES.DELETE_PRODUITUNITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PRODUITUNITES):
    case FAILURE(ACTION_TYPES.FETCH_PRODUITUNITE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUITUNITE):
    case FAILURE(ACTION_TYPES.CREATE_PRODUITUNITE):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUITUNITE):
    case FAILURE(ACTION_TYPES.DELETE_PRODUITUNITE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PRODUITUNITES):
    case SUCCESS(ACTION_TYPES.FETCH_PRODUITUNITE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUITUNITE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUITUNITE):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUITUNITE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUITUNITE):
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

const apiUrl = 'api/produit-unites';
const apiSearchUrl = 'api/_search/produit-unites';

// Actions

export const getSearchEntities: ICrudSearchAction<IProduitUnite> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PRODUITUNITES,
  payload: axios.get<IProduitUnite>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IProduitUnite> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRODUITUNITE_LIST,
  payload: axios.get<IProduitUnite>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProduitUnite> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUITUNITE,
    payload: axios.get<IProduitUnite>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProduitUnite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUITUNITE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProduitUnite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUITUNITE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProduitUnite> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUITUNITE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
