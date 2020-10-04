import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, IPayload } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMouvementStock, defaultValue } from 'app/shared/model/mouvement-stock.model';

export const ACTION_TYPES = {
  SEARCH_MOUVEMENTSTOCKS: 'mouvementStock/SEARCH_MOUVEMENTSTOCKS',
  FETCH_MOUVEMENTSTOCK_LIST: 'mouvementStock/FETCH_MOUVEMENTSTOCK_LIST',
  FETCH_FILTRED_MOUVEMENTSTOCK_LIST: 'mouvementStock/FETCH_FILTRED_MOUVEMENTSTOCK_LIST',
  FETCH_MOUVEMENTSTOCK: 'mouvementStock/FETCH_MOUVEMENTSTOCK',
  CREATE_MOUVEMENTSTOCK: 'mouvementStock/CREATE_MOUVEMENTSTOCK',
  UPDATE_MOUVEMENTSTOCK: 'mouvementStock/UPDATE_MOUVEMENTSTOCK',
  DELETE_MOUVEMENTSTOCK: 'mouvementStock/DELETE_MOUVEMENTSTOCK',
  RESET: 'mouvementStock/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMouvementStock>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MouvementStockState = Readonly<typeof initialState>;

// Reducer

export default (state: MouvementStockState = initialState, action): MouvementStockState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOUVEMENTSTOCKS):
    case REQUEST(ACTION_TYPES.FETCH_MOUVEMENTSTOCK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILTRED_MOUVEMENTSTOCK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOUVEMENTSTOCK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOUVEMENTSTOCK):
    case REQUEST(ACTION_TYPES.UPDATE_MOUVEMENTSTOCK):
    case REQUEST(ACTION_TYPES.DELETE_MOUVEMENTSTOCK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOUVEMENTSTOCKS):
    case FAILURE(ACTION_TYPES.FETCH_MOUVEMENTSTOCK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILTRED_MOUVEMENTSTOCK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOUVEMENTSTOCK):
    case FAILURE(ACTION_TYPES.CREATE_MOUVEMENTSTOCK):
    case FAILURE(ACTION_TYPES.UPDATE_MOUVEMENTSTOCK):
    case FAILURE(ACTION_TYPES.DELETE_MOUVEMENTSTOCK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOUVEMENTSTOCKS):
    case SUCCESS(ACTION_TYPES.FETCH_MOUVEMENTSTOCK_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_FILTRED_MOUVEMENTSTOCK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOUVEMENTSTOCK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOUVEMENTSTOCK):
    case SUCCESS(ACTION_TYPES.UPDATE_MOUVEMENTSTOCK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOUVEMENTSTOCK):
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

const apiUrl = 'api/mouvement-stocks';
const apiSearchUrl = 'api/_search/mouvement-stocks';

// Actions

export const getSearchEntities: ICrudSearchAction<IMouvementStock> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOUVEMENTSTOCKS,
  payload: axios.get<IMouvementStock>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IMouvementStock> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MOUVEMENTSTOCK_LIST,
    payload: axios.get<IMouvementStock>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getFilteredEntities: ICrudGetAllFiltredAction<IMouvementStock> = (filter, page, size, sort) => {
  return {
    type: ACTION_TYPES.FETCH_FILTRED_MOUVEMENTSTOCK_LIST,
    payload: axios.get<IMouvementStock>(`${apiUrl}?${filter}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
  };
};

export const getEntity: ICrudGetAction<IMouvementStock> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOUVEMENTSTOCK,
    payload: axios.get<IMouvementStock>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMouvementStock> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOUVEMENTSTOCK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMouvementStock> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOUVEMENTSTOCK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMouvementStock> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOUVEMENTSTOCK,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
