import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdresse, defaultValue } from 'app/shared/model/adresse.model';

export const ACTION_TYPES = {
  SEARCH_ADRESSES: 'adresse/SEARCH_ADRESSES',
  FETCH_ADRESSE_LIST: 'adresse/FETCH_ADRESSE_LIST',
  FETCH_ADRESSE: 'adresse/FETCH_ADRESSE',
  CREATE_ADRESSE: 'adresse/CREATE_ADRESSE',
  UPDATE_ADRESSE: 'adresse/UPDATE_ADRESSE',
  DELETE_ADRESSE: 'adresse/DELETE_ADRESSE',
  RESET: 'adresse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdresse>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AdresseState = Readonly<typeof initialState>;

// Reducer

export default (state: AdresseState = initialState, action): AdresseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ADRESSES):
    case REQUEST(ACTION_TYPES.FETCH_ADRESSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADRESSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADRESSE):
    case REQUEST(ACTION_TYPES.UPDATE_ADRESSE):
    case REQUEST(ACTION_TYPES.DELETE_ADRESSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ADRESSES):
    case FAILURE(ACTION_TYPES.FETCH_ADRESSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADRESSE):
    case FAILURE(ACTION_TYPES.CREATE_ADRESSE):
    case FAILURE(ACTION_TYPES.UPDATE_ADRESSE):
    case FAILURE(ACTION_TYPES.DELETE_ADRESSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ADRESSES):
    case SUCCESS(ACTION_TYPES.FETCH_ADRESSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADRESSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADRESSE):
    case SUCCESS(ACTION_TYPES.UPDATE_ADRESSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADRESSE):
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

const apiUrl = 'api/adresses';
const apiSearchUrl = 'api/_search/adresses';

// Actions

export const getSearchEntities: ICrudSearchAction<IAdresse> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ADRESSES,
  payload: axios.get<IAdresse>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IAdresse> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ADRESSE_LIST,
    payload: axios.get<IAdresse>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAdresse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADRESSE,
    payload: axios.get<IAdresse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdresse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADRESSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdresse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADRESSE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdresse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADRESSE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
