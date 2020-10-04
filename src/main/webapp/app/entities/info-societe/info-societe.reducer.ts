import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInfoSociete, defaultValue } from 'app/shared/model/info-societe.model';

export const ACTION_TYPES = {
  SEARCH_INFOSOCIETES: 'infoSociete/SEARCH_INFOSOCIETES',
  FETCH_INFOSOCIETE_LIST: 'infoSociete/FETCH_INFOSOCIETE_LIST',
  FETCH_INFOSOCIETE: 'infoSociete/FETCH_INFOSOCIETE',
  CREATE_INFOSOCIETE: 'infoSociete/CREATE_INFOSOCIETE',
  UPDATE_INFOSOCIETE: 'infoSociete/UPDATE_INFOSOCIETE',
  DELETE_INFOSOCIETE: 'infoSociete/DELETE_INFOSOCIETE',
  RESET: 'infoSociete/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInfoSociete>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type InfoSocieteState = Readonly<typeof initialState>;

// Reducer

export default (state: InfoSocieteState = initialState, action): InfoSocieteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_INFOSOCIETES):
    case REQUEST(ACTION_TYPES.FETCH_INFOSOCIETE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INFOSOCIETE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INFOSOCIETE):
    case REQUEST(ACTION_TYPES.UPDATE_INFOSOCIETE):
    case REQUEST(ACTION_TYPES.DELETE_INFOSOCIETE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_INFOSOCIETES):
    case FAILURE(ACTION_TYPES.FETCH_INFOSOCIETE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INFOSOCIETE):
    case FAILURE(ACTION_TYPES.CREATE_INFOSOCIETE):
    case FAILURE(ACTION_TYPES.UPDATE_INFOSOCIETE):
    case FAILURE(ACTION_TYPES.DELETE_INFOSOCIETE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_INFOSOCIETES):
    case SUCCESS(ACTION_TYPES.FETCH_INFOSOCIETE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INFOSOCIETE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INFOSOCIETE):
    case SUCCESS(ACTION_TYPES.UPDATE_INFOSOCIETE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INFOSOCIETE):
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

const apiUrl = 'api/info-societes';
const apiSearchUrl = 'api/_search/info-societes';

// Actions

export const getSearchEntities: ICrudSearchAction<IInfoSociete> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_INFOSOCIETES,
  payload: axios.get<IInfoSociete>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IInfoSociete> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INFOSOCIETE_LIST,
  payload: axios.get<IInfoSociete>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IInfoSociete> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INFOSOCIETE,
    payload: axios.get<IInfoSociete>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInfoSociete> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INFOSOCIETE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInfoSociete> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INFOSOCIETE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInfoSociete> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INFOSOCIETE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
