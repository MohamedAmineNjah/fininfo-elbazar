import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGestionFidelite, defaultValue } from 'app/shared/model/gestion-fidelite.model';

export const ACTION_TYPES = {
  SEARCH_GESTIONFIDELITES: 'gestionFidelite/SEARCH_GESTIONFIDELITES',
  FETCH_GESTIONFIDELITE_LIST: 'gestionFidelite/FETCH_GESTIONFIDELITE_LIST',
  FETCH_GESTIONFIDELITE: 'gestionFidelite/FETCH_GESTIONFIDELITE',
  CREATE_GESTIONFIDELITE: 'gestionFidelite/CREATE_GESTIONFIDELITE',
  UPDATE_GESTIONFIDELITE: 'gestionFidelite/UPDATE_GESTIONFIDELITE',
  DELETE_GESTIONFIDELITE: 'gestionFidelite/DELETE_GESTIONFIDELITE',
  RESET: 'gestionFidelite/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGestionFidelite>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GestionFideliteState = Readonly<typeof initialState>;

// Reducer

export default (state: GestionFideliteState = initialState, action): GestionFideliteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_GESTIONFIDELITES):
    case REQUEST(ACTION_TYPES.FETCH_GESTIONFIDELITE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GESTIONFIDELITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GESTIONFIDELITE):
    case REQUEST(ACTION_TYPES.UPDATE_GESTIONFIDELITE):
    case REQUEST(ACTION_TYPES.DELETE_GESTIONFIDELITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_GESTIONFIDELITES):
    case FAILURE(ACTION_TYPES.FETCH_GESTIONFIDELITE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GESTIONFIDELITE):
    case FAILURE(ACTION_TYPES.CREATE_GESTIONFIDELITE):
    case FAILURE(ACTION_TYPES.UPDATE_GESTIONFIDELITE):
    case FAILURE(ACTION_TYPES.DELETE_GESTIONFIDELITE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_GESTIONFIDELITES):
    case SUCCESS(ACTION_TYPES.FETCH_GESTIONFIDELITE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GESTIONFIDELITE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GESTIONFIDELITE):
    case SUCCESS(ACTION_TYPES.UPDATE_GESTIONFIDELITE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GESTIONFIDELITE):
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

const apiUrl = 'api/gestion-fidelites';
const apiSearchUrl = 'api/_search/gestion-fidelites';

// Actions

export const getSearchEntities: ICrudSearchAction<IGestionFidelite> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_GESTIONFIDELITES,
  payload: axios.get<IGestionFidelite>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IGestionFidelite> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GESTIONFIDELITE_LIST,
  payload: axios.get<IGestionFidelite>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGestionFidelite> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GESTIONFIDELITE,
    payload: axios.get<IGestionFidelite>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGestionFidelite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GESTIONFIDELITE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGestionFidelite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GESTIONFIDELITE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGestionFidelite> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GESTIONFIDELITE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
