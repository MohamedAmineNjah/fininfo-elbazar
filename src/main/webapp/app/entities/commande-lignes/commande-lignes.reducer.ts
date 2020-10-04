import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommandeLignes, defaultValue } from 'app/shared/model/commande-lignes.model';

export const ACTION_TYPES = {
  SEARCH_COMMANDELIGNES: 'commandeLignes/SEARCH_COMMANDELIGNES',
  FETCH_COMMANDELIGNES_LIST: 'commandeLignes/FETCH_COMMANDELIGNES_LIST',
  FETCH_COMMANDELIGNES: 'commandeLignes/FETCH_COMMANDELIGNES',
  CREATE_COMMANDELIGNES: 'commandeLignes/CREATE_COMMANDELIGNES',
  UPDATE_COMMANDELIGNES: 'commandeLignes/UPDATE_COMMANDELIGNES',
  DELETE_COMMANDELIGNES: 'commandeLignes/DELETE_COMMANDELIGNES',
  RESET: 'commandeLignes/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommandeLignes>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CommandeLignesState = Readonly<typeof initialState>;

// Reducer

export default (state: CommandeLignesState = initialState, action): CommandeLignesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COMMANDELIGNES):
    case REQUEST(ACTION_TYPES.FETCH_COMMANDELIGNES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMANDELIGNES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMANDELIGNES):
    case REQUEST(ACTION_TYPES.UPDATE_COMMANDELIGNES):
    case REQUEST(ACTION_TYPES.DELETE_COMMANDELIGNES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_COMMANDELIGNES):
    case FAILURE(ACTION_TYPES.FETCH_COMMANDELIGNES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMANDELIGNES):
    case FAILURE(ACTION_TYPES.CREATE_COMMANDELIGNES):
    case FAILURE(ACTION_TYPES.UPDATE_COMMANDELIGNES):
    case FAILURE(ACTION_TYPES.DELETE_COMMANDELIGNES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COMMANDELIGNES):
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDELIGNES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDELIGNES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMANDELIGNES):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMANDELIGNES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMANDELIGNES):
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

const apiUrl = 'api/commande-lignes';
const apiSearchUrl = 'api/_search/commande-lignes';

// Actions

export const getSearchEntities: ICrudSearchAction<ICommandeLignes> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COMMANDELIGNES,
  payload: axios.get<ICommandeLignes>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICommandeLignes> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMMANDELIGNES_LIST,
    payload: axios.get<ICommandeLignes>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICommandeLignes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMANDELIGNES,
    payload: axios.get<ICommandeLignes>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICommandeLignes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMANDELIGNES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommandeLignes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMANDELIGNES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommandeLignes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMANDELIGNES,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
