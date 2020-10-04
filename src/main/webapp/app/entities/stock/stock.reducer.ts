import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, IPayload } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStock, defaultValue } from 'app/shared/model/stock.model';

export const ACTION_TYPES = {
  SEARCH_STOCKS: 'stock/SEARCH_STOCKS',
  FETCH_STOCK_LIST: 'stock/FETCH_STOCK_LIST',
  FETCH_FILTRED_STOCK_LIST: 'stock/FETCH_FILTRED_STOCK_LIST',
  FETCH_STOCK: 'stock/FETCH_STOCK',
  CREATE_STOCK: 'stock/CREATE_STOCK',
  UPDATE_STOCK: 'stock/UPDATE_STOCK',
  DELETE_STOCK: 'stock/DELETE_STOCK',
  RESET: 'stock/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStock>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type StockState = Readonly<typeof initialState>;

// Reducer

export default (state: StockState = initialState, action): StockState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_STOCKS):
    case REQUEST(ACTION_TYPES.FETCH_STOCK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILTRED_STOCK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STOCK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STOCK):
    case REQUEST(ACTION_TYPES.UPDATE_STOCK):
    case REQUEST(ACTION_TYPES.DELETE_STOCK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_STOCKS):
    case FAILURE(ACTION_TYPES.FETCH_STOCK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILTRED_STOCK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STOCK):
    case FAILURE(ACTION_TYPES.CREATE_STOCK):
    case FAILURE(ACTION_TYPES.UPDATE_STOCK):
    case FAILURE(ACTION_TYPES.DELETE_STOCK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_STOCKS):
    case SUCCESS(ACTION_TYPES.FETCH_STOCK_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_FILTRED_STOCK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOCK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STOCK):
    case SUCCESS(ACTION_TYPES.UPDATE_STOCK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STOCK):
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

const apiUrl = 'api/stocks';
const apiSearchUrl = 'api/_search/stocks';

// Actions

export const getSearchEntities: ICrudSearchAction<IStock> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_STOCKS,
  payload: axios.get<IStock>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IStock> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STOCK_LIST,
    payload: axios.get<IStock>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getFilteredEntities: ICrudGetAllFiltredAction<IStock> = (filter, page, size, sort) => {
  return {
    type: ACTION_TYPES.FETCH_FILTRED_STOCK_LIST,
    payload: axios.get<IStock>(`${apiUrl}?${filter}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
  };
};

export const getEntity: ICrudGetAction<IStock> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STOCK,
    payload: axios.get<IStock>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStock> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STOCK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStock> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STOCK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStock> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STOCK,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
