import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISousCategorie, defaultValue } from 'app/shared/model/sous-categorie.model';

export const ACTION_TYPES = {
  SEARCH_SOUSCATEGORIES: 'sousCategorie/SEARCH_SOUSCATEGORIES',
  FETCH_SOUSCATEGORIE_LIST: 'sousCategorie/FETCH_SOUSCATEGORIE_LIST',
  FETCH_FILTRED_SOUSCATEGORIE_LIST: 'sousCategorie/FETCH_FILTRED_SOUSCATEGORIE_LIST',
  FETCH_SOUSCATEGORIE: 'sousCategorie/FETCH_SOUSCATEGORIE',
  CREATE_SOUSCATEGORIE: 'sousCategorie/CREATE_SOUSCATEGORIE',
  UPDATE_SOUSCATEGORIE: 'sousCategorie/UPDATE_SOUSCATEGORIE',
  DELETE_SOUSCATEGORIE: 'sousCategorie/DELETE_SOUSCATEGORIE',
  SET_BLOB: 'sousCategorie/SET_BLOB',
  RESET: 'sousCategorie/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISousCategorie>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SousCategorieState = Readonly<typeof initialState>;

// Reducer

export default (state: SousCategorieState = initialState, action): SousCategorieState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SOUSCATEGORIES):
    case REQUEST(ACTION_TYPES.FETCH_SOUSCATEGORIE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILTRED_SOUSCATEGORIE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOUSCATEGORIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOUSCATEGORIE):
    case REQUEST(ACTION_TYPES.UPDATE_SOUSCATEGORIE):
    case REQUEST(ACTION_TYPES.DELETE_SOUSCATEGORIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SOUSCATEGORIES):
    case FAILURE(ACTION_TYPES.FETCH_SOUSCATEGORIE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILTRED_SOUSCATEGORIE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOUSCATEGORIE):
    case FAILURE(ACTION_TYPES.CREATE_SOUSCATEGORIE):
    case FAILURE(ACTION_TYPES.UPDATE_SOUSCATEGORIE):
    case FAILURE(ACTION_TYPES.DELETE_SOUSCATEGORIE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SOUSCATEGORIES):
    case SUCCESS(ACTION_TYPES.FETCH_SOUSCATEGORIE_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_FILTRED_SOUSCATEGORIE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOUSCATEGORIE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOUSCATEGORIE):
    case SUCCESS(ACTION_TYPES.UPDATE_SOUSCATEGORIE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOUSCATEGORIE):
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

const apiUrl = 'api/sous-categories';
const apiSearchUrl = 'api/_search/sous-categories';
const filterCategorie = 'etat.equals=true';

// Actions

export const getSearchEntities: ICrudSearchAction<ISousCategorie> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SOUSCATEGORIES,
  payload: axios.get<ISousCategorie>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ISousCategorie> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SOUSCATEGORIE_LIST,
  payload: axios.get<ISousCategorie>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getFiltredEntities: ICrudGetAllAction<ISousCategorie> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FILTRED_SOUSCATEGORIE_LIST,
  payload: axios.get<ISousCategorie>(`${apiUrl}?${filterCategorie}`),
});

export const getEntity: ICrudGetAction<ISousCategorie> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOUSCATEGORIE,
    payload: axios.get<ISousCategorie>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISousCategorie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOUSCATEGORIE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISousCategorie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOUSCATEGORIE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISousCategorie> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOUSCATEGORIE,
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
