import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, IPayload } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAffectationZone, defaultValue } from 'app/shared/model/affectation-zone.model';

export const ACTION_TYPES = {
  SEARCH_AFFECTATIONZONES: 'affectationZone/SEARCH_AFFECTATIONZONES',
  FETCH_AFFECTATIONZONE_LIST: 'affectationZone/FETCH_AFFECTATIONZONE_LIST',
  FETCH_FILTRED_AFFECTATIONZONE_LIST: 'affectationZone/FETCH_FILTRED_AFFECTATIONZONE_LIST',
  FETCH_AFFECTATIONZONE: 'affectationZone/FETCH_AFFECTATIONZONE',
  CREATE_AFFECTATIONZONE: 'affectationZone/CREATE_AFFECTATIONZONE',
  UPDATE_AFFECTATIONZONE: 'affectationZone/UPDATE_AFFECTATIONZONE',
  DELETE_AFFECTATIONZONE: 'affectationZone/DELETE_AFFECTATIONZONE',
  RESET: 'affectationZone/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAffectationZone>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AffectationZoneState = Readonly<typeof initialState>;

// Reducer

export default (state: AffectationZoneState = initialState, action): AffectationZoneState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_AFFECTATIONZONES):
    case REQUEST(ACTION_TYPES.FETCH_AFFECTATIONZONE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILTRED_AFFECTATIONZONE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_AFFECTATIONZONE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_AFFECTATIONZONE):
    case REQUEST(ACTION_TYPES.UPDATE_AFFECTATIONZONE):
    case REQUEST(ACTION_TYPES.DELETE_AFFECTATIONZONE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_AFFECTATIONZONES):
    case FAILURE(ACTION_TYPES.FETCH_AFFECTATIONZONE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILTRED_AFFECTATIONZONE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_AFFECTATIONZONE):
    case FAILURE(ACTION_TYPES.CREATE_AFFECTATIONZONE):
    case FAILURE(ACTION_TYPES.UPDATE_AFFECTATIONZONE):
    case FAILURE(ACTION_TYPES.DELETE_AFFECTATIONZONE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_AFFECTATIONZONES):
    case SUCCESS(ACTION_TYPES.FETCH_AFFECTATIONZONE_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_FILTRED_AFFECTATIONZONE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_AFFECTATIONZONE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_AFFECTATIONZONE):
    case SUCCESS(ACTION_TYPES.UPDATE_AFFECTATIONZONE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_AFFECTATIONZONE):
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

const apiUrl = 'api/affectation-zones';
const apiSearchUrl = 'api/_search/affectation-zones';

// Actions

export const getSearchEntities: ICrudSearchAction<IAffectationZone> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_AFFECTATIONZONES,
  payload: axios.get<IAffectationZone>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IAffectationZone> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_AFFECTATIONZONE_LIST,
    payload: axios.get<IAffectationZone>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getFilteredEntities: ICrudGetAllFiltredAction<IAffectationZone> = (filter, page, size, sort) => {
  return {
    type: ACTION_TYPES.FETCH_FILTRED_AFFECTATIONZONE_LIST,
    payload: axios.get<IAffectationZone>(`${apiUrl}?${filter}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
  };
};

export const getEntity: ICrudGetAction<IAffectationZone> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_AFFECTATIONZONE,
    payload: axios.get<IAffectationZone>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAffectationZone> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_AFFECTATIONZONE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAffectationZone> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_AFFECTATIONZONE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAffectationZone> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_AFFECTATIONZONE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
